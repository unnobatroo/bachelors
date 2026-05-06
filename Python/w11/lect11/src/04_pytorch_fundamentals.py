# %% [markdown]
# # Lecture 11 Demo 4: PyTorch Fundamentals on Iris
#
# This script mirrors the redesigned slide stack 13-x:
#
# 1. neural-network intuition
# 2. why PyTorch exists
# 3. tensors and devices
# 4. gradient and autograd
# 5. layers, activations, logits, loss, optimizer
# 6. batches, epochs, training loop, evaluation
#
# Iris is intentionally small. The goal is not to beat scikit-learn's KNN;
# the goal is to make the PyTorch training loop inspectable.

# %%
from __future__ import annotations

import matplotlib.pyplot as plt
import torch
import torch.nn as nn
from sklearn.datasets import load_iris
from sklearn.metrics import accuracy_score
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from torch.utils.data import DataLoader, TensorDataset

from demo_utils import configure_static_theme, lecture_asset_path, section, select_device


SEED = 42

configure_static_theme()
torch.manual_seed(SEED)


# %% [markdown]
# ## 1. What is a neural network?
#
# A neural network is a stack of small mathematical units arranged in layers.
# Each connection has a learnable number called a weight. During training,
# PyTorch adjusts these weights so predictions become less wrong.

# %%
section("1. Neural-network intuition")

iris = load_iris(as_frame=True)
X = iris.data
y = iris.target

print("Iris input columns:")
for i, name in enumerate(X.columns, start=1):
    print(f"  x{i}: {name}")

print("\nIris output classes:")
for class_id, name in enumerate(iris.target_names):
    print(f"  output score {class_id}: {name}")

print(
    "\nA tiny Iris neural network will map 4 input features "
    "to 3 output scores, one score per species."
)


# %% [markdown]
# ## 2. Why use PyTorch here?
#
# For Iris, scikit-learn is still the practical first choice.
# PyTorch enters because it exposes the moving parts of a neural-network
# workflow: tensors, gradients, layers, loss, optimizers, and devices.

# %%
section("2. Why PyTorch?")

print("scikit-learn is excellent for small tabular baselines.")
print("PyTorch is useful when we need custom neural-network models.")
print("Today: use Iris to learn the loop; later: reuse the loop for images/text/audio.")


# %% [markdown]
# ## 3. Tensors: PyTorch's working object
#
# A tensor is a numeric array. It can be:
#
# - a scalar: one number
# - a vector: one row of numbers
# - a matrix: rows × columns
# - a higher-dimensional array: e.g. an image tensor
#
# Compared with a NumPy array, a tensor has two extra strengths:
#
# - it can live on a GPU
# - it can remember operations so gradients can be computed later

# %%
section("3. Tensors")

scalar = torch.tensor(7.0)
one_iris_row = torch.tensor(X.iloc[0].to_numpy(), dtype=torch.float32)
iris_matrix = torch.tensor(X.to_numpy(), dtype=torch.float32)

print(f"scalar:      {scalar}                 shape = {tuple(scalar.shape)}")
print(f"one row:     {one_iris_row}  shape = {tuple(one_iris_row.shape)}")
print(f"all rows:    shape = {tuple(iris_matrix.shape)}")
print(f"dtype:       {iris_matrix.dtype}")


# %% [markdown]
# ## 4. Devices: where tensors live
#
# A device is the hardware place where tensor math runs.
#
# - `cpu`: always available
# - `cuda`: NVIDIA GPU
# - `mps`: Apple Silicon GPU
#
# Important rule: tensors involved in one operation must be on the same device.

# %%
section("4. Device-aware code")

device = select_device()
print(f"Using device: {device}")

example_tensor = torch.tensor([1.0, 2.0, 3.0])
print(f"Before .to(device): {example_tensor.device}")

example_tensor = example_tensor.to(device)
print(f"After  .to(device): {example_tensor.device}")


# %% [markdown]
# ## 5. Prepare Iris for PyTorch
#
# The data science hygiene stays the same as earlier in the lecture:
#
# 1. split before learning anything
# 2. fit the scaler only on training data
# 3. convert the final numeric arrays to tensors
#
# `CrossEntropyLoss` expects class labels as integer IDs, so `y` uses
# `dtype=torch.long`.

# %%
section("5. Prepare Iris tensors")

X_train, X_test, y_train, y_test = train_test_split(
    X,
    y,
    test_size=0.2,
    random_state=SEED,
    stratify=y,
)

scaler = StandardScaler()
X_train_scaled = scaler.fit_transform(X_train)
X_test_scaled = scaler.transform(X_test)

X_train_tensor = torch.tensor(X_train_scaled, dtype=torch.float32)
y_train_tensor = torch.tensor(y_train.to_numpy(), dtype=torch.long)
X_test_tensor = torch.tensor(X_test_scaled, dtype=torch.float32)

print(f"X_train_tensor: {tuple(X_train_tensor.shape)}  float features")
print(f"y_train_tensor: {tuple(y_train_tensor.shape)}  integer class labels")
print(f"X_test_tensor:  {tuple(X_test_tensor.shape)}")


# %% [markdown]
# ## 6. Batch and epoch vocabulary
#
# A batch is a small group of rows processed together.
# An epoch is one complete pass through the training data.
#
# A `DataLoader` is the PyTorch object that hands us batches.

# %%
section("6. Batches and epochs")

train_loader = DataLoader(
    TensorDataset(X_train_tensor, y_train_tensor),
    batch_size=16,
    shuffle=True,
    # A fixed generator makes the classroom demo reproducible.
    generator=torch.Generator().manual_seed(SEED),
)

batch_X, batch_y = next(iter(train_loader))

print(f"One feature batch: {tuple(batch_X.shape)}")
print(f"One label batch:   {tuple(batch_y.shape)}")
print(f"First labels:      {batch_y[:8].tolist()}")


# %% [markdown]
# ## 7. Gradient: the direction of "less wrong"
#
# For one weight, a gradient answers:
#
# > If I nudge this weight a tiny bit, how much does the loss change?
#
# PyTorch computes gradients automatically with autograd.

# %%
section("7. Autograd in one equation")

# We ask PyTorch to track operations involving x.
x = torch.tensor(2.0, requires_grad=True)

# Forward pass: compute a result from x.
y_equation = x**2 + 3 * x

# Backward pass: compute the gradient dy/dx at x=2.
y_equation.backward()

print(f"x = {x.item():.1f}")
print(f"y = x^2 + 3x = {y_equation.item():.1f}")
print(f"gradient dy/dx at x=2 = {x.grad.item():.1f}")
print("Manual check: derivative of x^2 + 3x is 2x + 3, and 2*2 + 3 = 7.")


# %% [markdown]
# ## 8. Building block: one linear layer
#
# `nn.Linear(4, 16)` means:
#
# - receive 4 numbers per row
# - produce 16 learned combinations of those numbers
# - each combination has its own weights and bias

# %%
section("8. Linear layers and activations")

first_layer = nn.Linear(4, 16)
hidden_values = first_layer(batch_X)

print(first_layer)
print(f"Input batch shape:  {tuple(batch_X.shape)}")
print(f"Output batch shape: {tuple(hidden_values.shape)}")
print(f"Weight matrix:      {tuple(first_layer.weight.shape)}")
print(f"Bias vector:        {tuple(first_layer.bias.shape)}")

# ReLU is an activation function: max(0, z).
# It adds a non-linear bend between linear layers.
relu_values = nn.ReLU()(hidden_values)
print(f"After ReLU:         {tuple(relu_values.shape)}")


# %% [markdown]
# ## 9. Model, logits, loss, optimizer
#
# New terms:
#
# - logits: raw class scores from the final layer
# - loss: one number that measures how wrong the batch predictions are
# - optimizer: object that updates weights using gradients

# %%
section("9. Model, loss, optimizer")

# Reset the seed before creating the real model. The previous section created a
# demonstration layer, and layer creation consumes random numbers for weights.
torch.manual_seed(SEED)

model = nn.Sequential(
    nn.Linear(4, 16),
    nn.ReLU(),
    nn.Linear(16, 3),
).to(device)

criterion = nn.CrossEntropyLoss()
optimizer = torch.optim.AdamW(model.parameters(), lr=5e-3)

batch_X = batch_X.to(device)
batch_y = batch_y.to(device)

logits = model(batch_X)
loss = criterion(logits, batch_y)

print(model)
print(f"\nlogits shape: {tuple(logits.shape)}  # 16 rows, 3 class scores each")
print(f"one batch loss: {loss.item():.4f}")
print("optimizer:", optimizer.__class__.__name__)


# %% [markdown]
# ## 10. The full training loop
#
# The loop is the same one shown on the slides:
#
# 1. `zero_grad()` forget old gradients
# 2. forward pass: `logits = model(batch_X)`
# 3. loss: `criterion(logits, batch_y)`
# 4. backward pass: `loss.backward()`
# 5. optimizer step: `optimizer.step()`

# %%
section("10. Training loop")

epochs = 80
losses: list[float] = []

for epoch in range(epochs):
    # Training mode matters for some layers in larger networks.
    # It is harmless and correct to set it here even for this tiny model.
    model.train()

    running_loss = 0.0

    for batch_X, batch_y in train_loader:
        batch_X = batch_X.to(device)
        batch_y = batch_y.to(device)

        optimizer.zero_grad()
        logits = model(batch_X)
        loss = criterion(logits, batch_y)
        loss.backward()
        optimizer.step()

        # loss.item() is the average loss in this batch.
        # Multiply by batch size so the epoch average is weighted correctly.
        running_loss += loss.item() * len(batch_X)

    epoch_loss = running_loss / len(X_train_tensor)
    losses.append(epoch_loss)

    if epoch in {0, 9, 19, 39, 79}:
        print(f"epoch {epoch + 1:>2}: loss = {epoch_loss:.4f}")

fig, ax = plt.subplots(figsize=(8, 4.5), layout="constrained")
ax.plot(range(1, epochs + 1), losses, linewidth=2)
ax.set_title("Tiny Iris MLP training loss")
ax.set_xlabel("epoch")
ax.set_ylabel("cross-entropy loss")
ax.grid(alpha=0.3)
fig.savefig(lecture_asset_path("iris_loss_curve.png"), dpi=160, bbox_inches="tight")
plt.show()


# %% [markdown]
# ## 11. Evaluation and inference
#
# Evaluation is prediction without weight updates.
#
# - `model.eval()` switches the model into evaluation behavior
# - `torch.inference_mode()` disables autograd overhead
# - `argmax(dim=1)` chooses the class with the highest logit per row

# %%
section("11. Evaluation")

model.eval()
with torch.inference_mode():
    test_logits = model(X_test_tensor.to(device))
    pred = test_logits.argmax(dim=1).cpu().numpy()

test_accuracy = accuracy_score(y_test, pred)
print(f"Test accuracy: {test_accuracy:.3f}")

print("\nFirst five test predictions:")
for row_index, true_id, pred_id in zip(y_test.index[:5], y_test.iloc[:5], pred[:5], strict=True):
    true_name = iris.target_names[true_id]
    pred_name = iris.target_names[pred_id]
    marker = "OK" if true_id == pred_id else "MISS"
    print(f"row {row_index:>3}: true={true_name:<10} predicted={pred_name:<10} {marker}")


# %% [markdown]
# ## 12. Honest closing
#
# Iris is not where PyTorch shines. It is where the moving parts are small
# enough to inspect. The same vocabulary and loop carry over to MNIST images
# in the next demo.

# %%
section("12. Where PyTorch shines")

print("On Iris: scikit-learn KNN is simpler and already very strong.")
print("With images/text/audio: PyTorch's tensor + autograd + GPU workflow becomes valuable.")
print("Next demo: use the same loop to train on handwritten digit images (MNIST).")
