# %% [markdown]
# # Lecture 11 Demo 3: Computer Vision and Detection
#
# Aligned with the "Images Are Arrays" section of the lecture.
# This script also refreshes slide assets under `../assets/`.

# %%
from __future__ import annotations

import shutil

import matplotlib.pyplot as plt
from skimage import color, filters, io
from ultralytics import YOLO

from demo_utils import configure_static_theme, lecture_asset_path, section, source_path


configure_static_theme()

sobel_source = source_path("pexels-photo-378570.jpeg")
detection_source = source_path("pexels-pavel-danilyuk-7803682.jpg")
weights_path = source_path("yolov10n.pt")

# %% [markdown]
# ## 1. Images are arrays first

# %%
section("Load image and inspect its shape")

image = io.imread(sobel_source)

print(f"Image path:   {sobel_source.name}")
print(f"Array shape:  {image.shape}")
print(f"Array dtype:  {image.dtype}")

shutil.copy2(sobel_source, lecture_asset_path("sobel_input.jpg"))

fig, ax = plt.subplots(figsize=(6, 4), layout="constrained")
ax.imshow(image)
ax.set_title("A photo is still a numeric array")
ax.axis("off")
plt.show()

# %% [markdown]
# ## 2. Sobel filtering

# %%
section("Sobel edge detection")

gray = color.rgb2gray(image)
edges = filters.sobel(gray)

plt.imsave(lecture_asset_path("sobel_edges.png"), edges, cmap="gray")

fig, axes = plt.subplots(1, 2, figsize=(10, 4.5), layout="constrained")
axes[0].imshow(image)
axes[0].set_title("Input")
axes[0].axis("off")

axes[1].imshow(edges, cmap="gray")
axes[1].set_title("Sobel magnitude")
axes[1].axis("off")

plt.show()

# %% [markdown]
# ## 3. Local YOLO inference

# %%
section("Local object detection")

detection_input_asset = lecture_asset_path("detection_input.jpg")
detection_output_asset = lecture_asset_path("detection_result.jpg")
shutil.copy2(detection_source, detection_input_asset)

try:
    model = YOLO(str(weights_path))
    result = model(str(detection_source))[0]
    result.save(filename=str(detection_output_asset))

    if result.boxes is not None and len(result.boxes) > 0:
        labels = [result.names[int(cls)] for cls in result.boxes.cls.tolist()]
        print(f"Detected labels: {labels}")
    else:
        print("No boxes detected on this image, but the annotated result was still exported.")
except Exception as exc:  # pragma: no cover - defensive classroom fallback
    shutil.copy2(detection_source, detection_output_asset)
    print("Detection fallback used; copied the raw image instead of an annotated result.")
    print(f"Reason: {exc}")

result_image = io.imread(detection_output_asset)

fig, ax = plt.subplots(figsize=(7, 5), layout="constrained")
ax.imshow(result_image)
ax.set_title("Detection result asset")
ax.axis("off")
plt.show()
