# %% [markdown]
# # Lecture 11 Demo 5: PyTorch on MNIST Handwritten Digits
#
# This demo applies the same PyTorch vocabulary from Demo 4 to a more visual
# task: recognizing handwritten digits from 28x28 grayscale images.
#
# Teaching goal:
#
# - students already know images are arrays from the computer-vision section
# - students just learned tensors, logits, loss, optimizer, batches, epochs
# - this script shows that the same training loop can train an image classifier
#
# First run note:
#
# - `torchvision.datasets.MNIST(download=True)` downloads the dataset into
#   `src/.data/MNIST/`.
# - After the first successful download, it can be reused offline from disk.

# %%
from __future__ import annotations

from dataclasses import dataclass

import matplotlib.pyplot as plt
import torch
import torch.nn as nn
from torch.utils.data import DataLoader, Subset
from torchvision import datasets, transforms

from demo_utils import HERE, configure_static_theme, lecture_asset_path, section, select_device


# Keep the classroom demo small enough to run quickly on CPU.
# MNIST has 60,000 training images and 10,000 test images. We use subsets here
# so students can run the demo during class without waiting too long.
SEED = 42
TRAIN_LIMIT = 10_000
TEST_LIMIT = 2_000
BATCH_SIZE = 128
EPOCHS = 3
LEARNING_RATE = 1e-3
DATA_DIR = HERE / ".data"


@dataclass(frozen=True)
class EpochMetrics:
    """Simple container for the numbers we want to plot after training."""

    train_loss: float
    test_accuracy: float


def set_seed(seed: int) -> None:
    """Make the demo reproducible enough for classroom output.

    Exact bit-for-bit reproducibility across CPU, CUDA, and MPS is not always
    guaranteed, but this removes the biggest source of randomness.
    """

    torch.manual_seed(seed)
    if torch.cuda.is_available():
        torch.cuda.manual_seed_all(seed)


def class_name(label: int) -> str:
    """MNIST labels are already the digit IDs 0..9."""

    return str(label)


# %% [markdown]
# ## 1. The task in plain language
#
# MNIST contains small grayscale images of handwritten digits.
#
# - Input: one 28x28 image
# - Target label: the digit written in the image, from 0 to 9
# - Model output: 10 raw scores, one score per possible digit
#
# This is supervised learning: we train from images with known labels.

# %%
section("1. Task")

configure_static_theme()
set_seed(SEED)
device = select_device()

print("Task: handwritten digit classification")
print("Input: 28 x 28 grayscale image")
print("Target: integer label from 0 to 9")
print("Output: 10 logits, one score per digit")
print(f"Using device: {device}")


# %% [markdown]
# ## 2. Load MNIST as tensors
#
# `transforms.ToTensor()` converts a PIL image into a PyTorch tensor.
#
# Shape after conversion:
#
# - `1`: one channel, because MNIST is grayscale
# - `28`: image height
# - `28`: image width
#
# Values are floats in the range `[0, 1]`.

# %%
section("2. Load MNIST")

to_tensor = transforms.ToTensor()

try:
    train_full = datasets.MNIST(
        root=DATA_DIR,
        train=True,
        download=True,
        transform=to_tensor,
    )
    test_full = datasets.MNIST(
        root=DATA_DIR,
        train=False,
        download=True,
        transform=to_tensor,
    )
except RuntimeError as exc:
    raise RuntimeError(
        "MNIST could not be downloaded. Run this once with internet access, "
        "or copy a prepared MNIST folder into 11_AI_ML_DS_3/src/.data/."
    ) from exc

train_data = Subset(train_full, range(TRAIN_LIMIT))
test_data = Subset(test_full, range(TEST_LIMIT))

image, label = train_full[0]

print(f"Full training images: {len(train_full):,}")
print(f"Full test images:     {len(test_full):,}")
print(f"Demo training subset: {len(train_data):,}")
print(f"Demo test subset:     {len(test_data):,}")
print(f"One image shape:      {tuple(image.shape)}  # channel, height, width")
print(f"One label:            {label}  # the handwritten digit")
print(f"Pixel min/max:        {image.min().item():.1f} .. {image.max().item():.1f}")


# %% [markdown]
# ## 3. Visualize a few examples
#
# Before training, always inspect examples. This is the image equivalent of
# looking at the first few rows of a DataFrame.

# %%
section("3. Inspect examples")

fig, axes = plt.subplots(2, 5, figsize=(8, 3.6), layout="constrained")
for ax, index in zip(axes.ravel(), range(10), strict=True):
    digit_image, digit_label = train_full[index]

    # The tensor shape is (1, 28, 28). Matplotlib wants just (28, 28), so we
    # remove the channel dimension with squeeze(0).
    ax.imshow(digit_image.squeeze(0), cmap="gray")
    ax.set_title(f"label: {class_name(digit_label)}")
    ax.axis("off")

fig.suptitle("MNIST examples: image tensors with known labels")
fig.savefig(lecture_asset_path("mnist_digit_examples.png"), dpi=160, bbox_inches="tight")
plt.show()


# %% [markdown]
# ## 4. DataLoader: batches for images
#
# The training loop should not process one image at a time.
# A `DataLoader` gives us mini-batches:
#
# - image batch shape: `(batch_size, 1, 28, 28)`
# - label batch shape: `(batch_size,)`

# %%
section("4. DataLoader")

train_loader = DataLoader(
    train_data,
    batch_size=BATCH_SIZE,
    shuffle=True,
    num_workers=0,
    generator=torch.Generator().manual_seed(SEED),
)
test_loader = DataLoader(
    test_data,
    batch_size=BATCH_SIZE,
    shuffle=False,
    num_workers=0,
)

batch_images, batch_labels = next(iter(train_loader))

print(f"Image batch shape: {tuple(batch_images.shape)}")
print(f"Label batch shape: {tuple(batch_labels.shape)}")
print(f"First 12 labels:   {batch_labels[:12].tolist()}")


# %% [markdown]
# ## 5. First model: flatten pixels, then use linear layers
#
# A 28x28 image contains 784 pixels.
#
# This first model deliberately keeps the architecture simple:
#
# 1. flatten `(1, 28, 28)` into `784`
# 2. apply a linear layer
# 3. apply ReLU
# 4. apply another linear layer
# 5. output 10 logits, one per digit
#
# A convolutional network would preserve spatial neighborhoods better, but the
# simple model is ideal for connecting MNIST to the concepts from slide stack 13.

# %%
section("5. Define a small digit classifier")


class DigitMLP(nn.Module):
    """A minimal neural network for 28x28 MNIST images.

    `nn.Module` is PyTorch's base class for models. Subclassing it gives us a
    clean place to define layers in `__init__` and the forward pass in
    `forward`.
    """

    def __init__(self) -> None:
        super().__init__()

        # Flatten turns an image tensor from (batch, 1, 28, 28)
        # into a 2-D tensor with shape (batch, 784).
        self.flatten = nn.Flatten()

        # The layer stack maps 784 pixel values to 10 digit scores.
        self.network = nn.Sequential(
            nn.Linear(28 * 28, 128),
            nn.ReLU(),
            nn.Linear(128, 64),
            nn.ReLU(),
            nn.Linear(64, 10),
        )

    def forward(self, images: torch.Tensor) -> torch.Tensor:
        """Turn a batch of images into a batch of logits.

        Input shape:
            (batch_size, 1, 28, 28)
        Output shape:
            (batch_size, 10)
        """

        pixels = self.flatten(images)
        logits = self.network(pixels)
        return logits


model = DigitMLP().to(device)
print(model)


# %% [markdown]
# ## 6. Dry run before training
#
# A dry run checks shapes before we spend time training.
#
# `CrossEntropyLoss` expects:
#
# - logits with shape `(batch_size, number_of_classes)`
# - integer labels with shape `(batch_size,)`

# %%
section("6. Dry run: shapes and one loss")

criterion = nn.CrossEntropyLoss()
optimizer = torch.optim.AdamW(model.parameters(), lr=LEARNING_RATE)

batch_images = batch_images.to(device)
batch_labels = batch_labels.to(device)

logits = model(batch_images)
loss = criterion(logits, batch_labels)

print(f"Input images: {tuple(batch_images.shape)}")
print(f"Logits:       {tuple(logits.shape)}")
print(f"Labels:       {tuple(batch_labels.shape)}")
print(f"Initial loss: {loss.item():.4f}")

probabilities = logits.softmax(dim=1)
predicted_digits = probabilities.argmax(dim=1)
print(f"First 12 predictions before training: {predicted_digits[:12].tolist()}")


# %% [markdown]
# ## 7. Training and evaluation helpers
#
# We split the logic into two functions:
#
# - `train_one_epoch`: update weights from training batches
# - `evaluate`: measure accuracy without updating weights

# %%
section("7. Training helpers")


def train_one_epoch() -> float:
    """Train for one pass through the training subset and return average loss."""

    model.train()
    total_loss = 0.0
    total_examples = 0

    for images, labels in train_loader:
        images = images.to(device)
        labels = labels.to(device)

        # Step 1: clear gradients from the previous batch.
        optimizer.zero_grad()

        # Step 2: forward pass. The model produces 10 logits per image.
        logits = model(images)

        # Step 3: compare logits with true digit labels.
        loss = criterion(logits, labels)

        # Step 4: backward pass. Autograd computes gradients for every weight.
        loss.backward()

        # Step 5: optimizer step. AdamW nudges weights to reduce future loss.
        optimizer.step()

        batch_size = images.shape[0]
        total_loss += loss.item() * batch_size
        total_examples += batch_size

    return total_loss / total_examples


def evaluate() -> float:
    """Return accuracy on the test subset."""

    model.eval()
    correct = 0
    total = 0

    # During evaluation, we only want predictions. No gradients are needed.
    with torch.inference_mode():
        for images, labels in test_loader:
            images = images.to(device)
            labels = labels.to(device)

            logits = model(images)
            predictions = logits.argmax(dim=1)

            correct += (predictions == labels).sum().item()
            total += labels.shape[0]

    return correct / total


print("Helpers ready: train_one_epoch() and evaluate()")


# %% [markdown]
# ## 8. Train the model
#
# Watch two numbers:
#
# - training loss should generally go down
# - test accuracy should generally go up
#
# This is the same feedback loop from the slides: forward → loss → backward → step.

# %%
section("8. Train")

history: list[EpochMetrics] = []

for epoch in range(1, EPOCHS + 1):
    train_loss = train_one_epoch()
    test_accuracy = evaluate()
    history.append(EpochMetrics(train_loss=train_loss, test_accuracy=test_accuracy))

    print(
        f"epoch {epoch}: "
        f"train loss = {train_loss:.4f}, "
        f"test accuracy = {test_accuracy:.3f}"
    )


# %% [markdown]
# ## 9. Plot the training story

# %%
section("9. Plot metrics")

epochs = range(1, len(history) + 1)
train_losses = [row.train_loss for row in history]
test_accuracies = [row.test_accuracy for row in history]

fig, (ax_loss, ax_acc) = plt.subplots(1, 2, figsize=(10, 4), layout="constrained")

ax_loss.plot(epochs, train_losses, marker="o", linewidth=2)
ax_loss.set_title("MNIST training loss")
ax_loss.set_xlabel("epoch")
ax_loss.set_ylabel("cross-entropy loss")
ax_loss.grid(alpha=0.3)

ax_acc.plot(epochs, test_accuracies, marker="o", linewidth=2)
ax_acc.set_title("MNIST test accuracy")
ax_acc.set_xlabel("epoch")
ax_acc.set_ylabel("accuracy")
ax_acc.set_ylim(0, 1)
ax_acc.grid(alpha=0.3)

fig.savefig(lecture_asset_path("mnist_loss_accuracy.png"), dpi=160, bbox_inches="tight")
plt.show()


# %% [markdown]
# ## 10. Inspect predictions
#
# Accuracy is useful, but always inspect actual examples too.
# The title above each image shows:
#
# - `p`: predicted digit
# - `t`: true digit

# %%
section("10. Inspect predictions")

model.eval()
sample_images, sample_labels = next(iter(test_loader))

with torch.inference_mode():
    sample_logits = model(sample_images.to(device))
    sample_predictions = sample_logits.argmax(dim=1).cpu()

fig, axes = plt.subplots(3, 4, figsize=(7, 5), layout="constrained")
for ax, image, true_label, pred_label in zip(
    axes.ravel(),
    sample_images[:12],
    sample_labels[:12],
    sample_predictions[:12],
    strict=True,
):
    is_correct = int(true_label) == int(pred_label)
    title_color = "#1e5128" if is_correct else "#b91c1c"

    ax.imshow(image.squeeze(0), cmap="gray")
    ax.set_title(f"p:{int(pred_label)}  t:{int(true_label)}", color=title_color)
    ax.axis("off")

fig.suptitle("MNIST predictions: green title = correct, red title = mistake")
fig.savefig(lecture_asset_path("mnist_predictions.png"), dpi=160, bbox_inches="tight")
plt.show()


# %% [markdown]
# ## 11. What this example teaches
#
# MNIST makes PyTorch feel more natural than Iris:
#
# - the inputs are images, not a tiny table
# - each example has 784 pixel values
# - the same training loop learns useful weights
# - later, a convolutional neural network can improve by using image structure

# %%
section("11. Takeaways")

print("Same vocabulary as Iris: tensors, logits, loss, gradients, optimizer.")
print("New data type: image tensors with shape (channel, height, width).")
print("This model flattens images; CNNs go further by preserving spatial neighborhoods.")
print("Generated assets:")
print("  - assets/mnist_digit_examples.png")
print("  - assets/mnist_loss_accuracy.png")
print("  - assets/mnist_predictions.png")
