# %% [markdown]
# # Lecture 11 Demo 1: scikit-learn Workflow
#
# Aligned with the "From Tables to Models" section of the lecture.
# Run cell by cell in JupyterLab, Cursor, or VS Code interactive mode.

# %%
from __future__ import annotations

import json

import joblib
import matplotlib.pyplot as plt
import pandas as pd
from sklearn.datasets import load_iris
from sklearn.metrics import accuracy_score, classification_report
from sklearn.model_selection import cross_val_score, train_test_split
from sklearn.neighbors import KNeighborsClassifier
from sklearn.pipeline import make_pipeline
from sklearn.preprocessing import StandardScaler

from demo_utils import configure_static_theme, section, source_path


configure_static_theme()

section("Versions")
print(f"pandas: {pd.__version__}")
print("Demo path: name the ML task, build X and y, introduce the estimator API, then evaluate honestly.")

# %% [markdown]
# ## 1. Load a small but inspectable dataset and name the task

# %%
section("Load Iris as a DataFrame")

iris = load_iris(as_frame=True)
X = iris.data
y = iris.target
class_names = [str(name) for name in iris.target_names]

column_roles = pd.DataFrame(
    {
        "column": [*X.columns, "species"],
        "role": ["feature", "feature", "feature", "feature", "target label"],
    }
)

print("Row / sample / observation: one Iris flower measurement.")
print("Features: numeric input columns that describe the flower.")
print("Target / label: the species we want to predict.")
print("\nColumn roles:")
print(column_roles.to_string(index=False))
print("\nFirst rows:")
print(X.head().to_string())

# %% [markdown]
# ## 2. Build X and y

# %%
section("From DataFrame to X and y")

print(f"X shape: {X.shape} = (n_samples, n_features)")
print(f"y shape: {y.shape} = one target label per row")
print(f"Classes: {class_names}")
print("Most scikit-learn estimators expect numeric feature columns.")

# %% [markdown]
# ## 3. Introduce the estimator API

# %%
section("Estimator API shape")

knn_estimator = KNeighborsClassifier(n_neighbors=5)

print(f"Estimator class: {knn_estimator.__class__.__name__}")
print(f"Hyperparameter n_neighbors: {knn_estimator.get_params()['n_neighbors']}")
print('"Model" is the intuitive word; "estimator" is scikit-learn\'s precise API term.')
print("Shared predictor API: fit(X_train, y_train), then predict(X_test).")

# %% [markdown]
# ## 4. Split first, then learn

# %%
section("Train / test split")

X_train, X_test, y_train, y_test = train_test_split(
    X,
    y,
    test_size=0.2,
    random_state=42,
    stratify=y,
)

print(f"Train rows: {len(X_train)}")
print(f"Test rows:  {len(X_test)}")
print("The test set now acts as a final exam. It stays untouched while choosing k.")

# %% [markdown]
# ## 5. Keep scaling inside the workflow

# %%
section("Build a pipeline")

baseline_pipe = make_pipeline(
    StandardScaler(),
    KNeighborsClassifier(n_neighbors=5),
)

baseline_pipe.fit(X_train, y_train)
baseline_pred = baseline_pipe.predict(X_test)
baseline_acc = accuracy_score(y_test, baseline_pred)

print(f"Pipeline test accuracy with k=5: {baseline_acc:.3f}")

# %% [markdown]
# ## 6. Choose k on the training data with cross-validation

# %%
section("Cross-validation over k")

k_values = list(range(1, 16))
cv_scores: list[float] = []

for k in k_values:
    pipe = make_pipeline(
        StandardScaler(),
        KNeighborsClassifier(n_neighbors=k),
    )
    score = cross_val_score(pipe, X_train, y_train, cv=5).mean()
    cv_scores.append(score)

best_index = max(range(len(cv_scores)), key=cv_scores.__getitem__)
best_k = k_values[best_index]
best_cv = cv_scores[best_index]

print(f"Best k from cross-validation: {best_k}")
print(f"Best mean CV accuracy:        {best_cv:.3f}")

fig, ax = plt.subplots(figsize=(8, 4.5), layout="constrained")
ax.plot(k_values, cv_scores, marker="o", linewidth=2)
ax.axvline(best_k, linestyle="--", linewidth=1.5, color="#2f7c3e", label=f"best k = {best_k}")
ax.set_title("Tune k on the training data, not on the test set")
ax.set_xlabel("k neighbors")
ax.set_ylabel("mean CV accuracy")
ax.grid(alpha=0.3)
ax.legend()
if "agg" not in plt.get_backend().lower():
    plt.show(block=False)
    plt.pause(0.1)
plt.close(fig)

# %% [markdown]
# ## 7. Final fit and evaluation

# %%
section("Final fit and test evaluation")

final_pipe = make_pipeline(
    StandardScaler(),
    KNeighborsClassifier(n_neighbors=best_k),
)
final_pipe.fit(X_train, y_train)
test_pred = final_pipe.predict(X_test)
test_acc = accuracy_score(y_test, test_pred)

print(f"Final test accuracy: {test_acc:.3f}")
print("\nClassification report:")
print(classification_report(y_test, test_pred, target_names=iris.target_names))

# %% [markdown]
# ## 8. Persist the whole workflow

# %%
section("Persist the fitted pipeline")

artifact_path = source_path("iris_knn_model_v1.joblib")
joblib.dump(final_pipe, artifact_path)

metadata = {
    "dataset": "iris",
    "best_k": best_k,
    "test_accuracy": round(float(test_acc), 4),
    "note": "This artifact is safe to load only from trusted sources and should travel with dependency metadata.",
}
metadata_path = artifact_path.with_suffix(".json")
metadata_path.write_text(json.dumps(metadata, indent=2))

print(f"Saved pipeline to: {artifact_path.name}")
print(f"Saved metadata to: {metadata_path.name}")
