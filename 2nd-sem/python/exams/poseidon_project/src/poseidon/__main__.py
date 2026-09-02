from poseidon.main import app

__all__ = ["app"]


def main() -> None:
    # run the app with uvicorn when executed as a script
    import uvicorn

    uvicorn.run("poseidon.main:app", host="0.0.0.0", port=8000, reload=True)


if __name__ == "__main__":
    # entrypoint for `python -m poseidon`
    main()
