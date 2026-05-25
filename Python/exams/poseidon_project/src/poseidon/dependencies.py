from __future__ import annotations

from typing import Annotated

from fastapi import Depends

from poseidon.vessel_manager import VesselManager

_vessel_manager: VesselManager | None = None


def init_dependencies(manager: VesselManager) -> None:
    # set the module-level vessel manager used by dependency providers
    global _vessel_manager
    _vessel_manager = manager


def reset_dependencies() -> None:
    # clear the stored manager (used on shutdown/tests)
    global _vessel_manager
    _vessel_manager = None


def get_vessel_manager() -> VesselManager:
    # return the initialized vessel manager or raise if not ready
    if _vessel_manager is None:
        raise RuntimeError("VesselManager not initialised")
    return _vessel_manager


VesselManagerDep = Annotated[VesselManager, Depends(get_vessel_manager)]
