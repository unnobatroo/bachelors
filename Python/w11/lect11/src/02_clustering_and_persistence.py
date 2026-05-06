# %% [markdown]
# # Lecture 11 Demo 2: Clustering and Persistence
#
# Aligned with the "Discovery and Handoff" section of the lecture.

# %%
from __future__ import annotations

import json

import joblib
import matplotlib.pyplot as plt
import pandas as pd
from sklearn.datasets import load_iris
from sklearn.cluster import KMeans
from sklearn.metrics import silhouette_score
from sklearn.preprocessing import StandardScaler

from demo_utils import configure_static_theme, section, source_path


configure_static_theme()

section("Versions")
print(f"pandas: {pd.__version__}")
print("This demo keeps the clustering story visual and uses persistence as a production handoff checkpoint.")

# %% [markdown]
# ## 1. Use two interpretable features for a simple visual clustering demo

# %%
section("Iris petal features")

iris = load_iris(as_frame=True)
features = iris.data[["petal length (cm)", "petal width (cm)"]]

scaler = StandardScaler()
X_scaled = scaler.fit_transform(features)

print(features.head().to_string())

# %% [markdown]
# ## 2. Fit K-Means and inspect the clusters

# %%
section("K-Means with k=3")

kmeans = KMeans(n_clusters=3, random_state=42, n_init="auto")
labels = kmeans.fit_predict(X_scaled)

fig, ax = plt.subplots(figsize=(7, 5), layout="constrained")
scatter = ax.scatter(
    features["petal length (cm)"],
    features["petal width (cm)"],
    c=labels,
    cmap="viridis",
    s=45,
    alpha=0.85,
)
ax.set_title("K-Means clusters on Iris petal features")
ax.set_xlabel("petal length (cm)")
ax.set_ylabel("petal width (cm)")
ax.grid(alpha=0.3)
plt.show()

# %% [markdown]
# ## 3. Elbow plus silhouette: better than elbow alone

# %%
section("Search over k")

k_values = list(range(2, 9))
inertia_values: list[float] = []
silhouette_values: list[float] = []

for k in k_values:
    model = KMeans(n_clusters=k, random_state=42, n_init="auto")
    current_labels = model.fit_predict(X_scaled)
    inertia_values.append(model.inertia_)
    silhouette_values.append(silhouette_score(X_scaled, current_labels))

results = pd.DataFrame(
    {
        "k": k_values,
        "inertia": inertia_values,
        "silhouette": silhouette_values,
    }
)
print(results.round(3).to_string(index=False))

fig, (ax_left, ax_right) = plt.subplots(1, 2, figsize=(10.5, 4.5), layout="constrained")
ax_left.plot(k_values, inertia_values, marker="o", linewidth=2)
ax_left.set_title("Elbow heuristic")
ax_left.set_xlabel("k clusters")
ax_left.set_ylabel("inertia")
ax_left.grid(alpha=0.3)

ax_right.plot(k_values, silhouette_values, marker="o", linewidth=2, color="#2f7c3e")
ax_right.set_title("Silhouette score")
ax_right.set_xlabel("k clusters")
ax_right.set_ylabel("silhouette")
ax_right.grid(alpha=0.3)

plt.show()

# %% [markdown]
# ## 4. Production handoff: reload a saved pipeline

# %%
section("Reload the supervised pipeline")

artifact_path = source_path("iris_knn_model_v1.joblib")
metadata_path = source_path("iris_knn_model_v1.json")

pipe = joblib.load(artifact_path)
metadata = json.loads(metadata_path.read_text())

sample = iris.data.iloc[[0, 70, 140]]
pred = pipe.predict(sample)

print("Saved artifact metadata:")
print(json.dumps(metadata, indent=2))
print("\nSample predictions from the reloaded pipeline:")
for row_index, target_id in zip(sample.index, pred, strict=True):
    print(f"row {row_index:>3} -> {iris.target_names[target_id]}")

print("\nReminder: loading pickle/joblib artifacts is safe only when the file comes from a trusted source.")
