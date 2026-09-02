# Lecture 11 Demo Source

This folder contains the classroom demo source code aligned with Lecture 11: applied Python for machine learning, computer vision, and PyTorch.

## Canonical demo sequence

- `01_sklearn_workflow.py`
  - Notebook companion: `01_sklearn_workflow.ipynb`
  - Slides: rows/features/targets, `X` and `y`, estimator API, Iris, KNN, train/test split, pipelines, cross-validation
  - Focus: student-facing scikit-learn vocabulary, honest evaluation, and workflow safety
- `02_clustering_and_persistence.py`
  - Notebook companion: `02_clustering_and_persistence.ipynb`
  - Slides: K-Means, elbow heuristic, persistence and handoff
  - Focus: unsupervised discovery plus trusted artifact handling
- `03_computer_vision_and_detection.py`
  - Notebook companion: `03_computer_vision_and_detection.ipynb`
  - Slides: images as arrays, Sobel filtering, local YOLO inference
  - Focus: NumPy-first image reasoning and predictable classroom demos
- `04_pytorch_fundamentals.py`
  - Notebook companion: `04_pytorch_fundamentals.ipynb`
  - Slides: neural-network intuition, PyTorch use cases, tensors, devices, gradients, autograd, layers, loss/optimizer, batches/epochs, training loop, evaluation mode
  - Focus: a minimal, inspectable PyTorch workflow on Iris aligned with slide stack 13-x
- `05_mnist_pytorch_digits.py`
  - Notebook companion: `05_mnist_pytorch_digits.ipynb`
  - Extension: use the same PyTorch loop to train a handwritten-digit classifier on MNIST
  - Focus: image tensors, DataLoader batches, logits for 10 classes, training loss, test accuracy, and visual inspection of predictions

These scripts use `# %%` cell markers so they work well in:

- JupyterLab
- Cursor / VS Code interactive execution
- plain `python` runs for quick smoke tests

## Shared helper

- `demo_utils.py`
  - lecture paths and asset helpers
  - plotting theme
  - device selection helper
  - notebook export helper

## Reused local assets

- `pexels-photo-378570.jpeg`
- `pexels-pavel-danilyuk-7803682.jpg`
- `yolov10n.pt`
- `iris_knn_model_v1.joblib` (legacy artifact from the earlier notebook)

The canonical scripts generate refreshed lecture assets under `../assets/` where needed, for example:

- `sobel_input.jpg`
- `sobel_edges.png`
- `detection_input.jpg`
- `detection_result.jpg`

## Quickstart

From this directory:

```bash
uv sync
uv run python 01_sklearn_workflow.py
uv run python 02_clustering_and_persistence.py
uv run python 03_computer_vision_and_detection.py
uv run python 04_pytorch_fundamentals.py
uv run python 05_mnist_pytorch_digits.py
```

For classroom use, interactive execution is usually nicer:

```bash
uv run jupyter lab
```

Then open any of:

- `01_sklearn_workflow.ipynb`
- `02_clustering_and_persistence.ipynb`
- `03_computer_vision_and_detection.ipynb`
- `04_pytorch_fundamentals.ipynb`
- `05_mnist_pytorch_digits.ipynb`

## Notes

- The numbered scripts are the canonical live-demo path.
- All new demos are designed to work offline once dependencies are installed and the bundled local assets are present.
- The MNIST demo downloads dataset files to `src/.data/` on first run; that local cache is ignored by git and can be reused offline afterward.
