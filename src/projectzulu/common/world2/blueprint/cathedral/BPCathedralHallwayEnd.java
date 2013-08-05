package projectzulu.common.world2.blueprint.cathedral;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.ChunkCoordinates;
import projectzulu.common.world.CellIndexDirection;
import projectzulu.common.world.dataobjects.BlockWithMeta;
import projectzulu.common.world2.BoundaryPair;
import projectzulu.common.world2.CellHelper;
import projectzulu.common.world2.blueprint.Blueprint;

public class BPCathedralHallwayEnd implements Blueprint {

    @Override
    public BlockWithMeta getBlockFromBlueprint(ChunkCoordinates piecePos, int cellSize, int cellHeight, Random random,
            CellIndexDirection cellIndexDirection) {
        piecePos = applyMirror(piecePos, cellSize, cellIndexDirection);
        piecePos = applyRotation(piecePos, cellSize, cellIndexDirection);
        return getWallBlock(CellHelper.rotateCellTo(piecePos, cellSize, CellIndexDirection.NorthWall), cellSize,
                cellHeight, random, cellIndexDirection);
    }

    private ChunkCoordinates applyMirror(ChunkCoordinates piecePos, int cellSize, CellIndexDirection cellIndexDirection) {
        if (cellIndexDirection == CellIndexDirection.NorthWestCorner
                || cellIndexDirection == CellIndexDirection.NorthEastCorner
                || cellIndexDirection == CellIndexDirection.SouthEastCorner
                || cellIndexDirection == CellIndexDirection.SouthWestCorner) {
            piecePos = CellHelper.mirrorCellTo(piecePos, cellSize, CellIndexDirection.SouthWestCorner);
        }
        return piecePos;
    }

    private ChunkCoordinates applyRotation(ChunkCoordinates piecePos, int cellSize,
            CellIndexDirection cellIndexDirection) {
        if (cellIndexDirection == CellIndexDirection.NorthWestCorner) {
            piecePos = CellHelper.rotateCellTo(piecePos, cellSize, CellIndexDirection.NorthWall);
        } else if (cellIndexDirection == CellIndexDirection.NorthEastCorner) {
            piecePos = CellHelper.rotateCellTo(piecePos, cellSize, CellIndexDirection.WestWall);
        } else if (cellIndexDirection == CellIndexDirection.SouthEastCorner) {
            piecePos = CellHelper.rotateCellTo(piecePos, cellSize, CellIndexDirection.SouthWall);
        } else if (cellIndexDirection == CellIndexDirection.SouthWestCorner) {
            piecePos = CellHelper.rotateCellTo(piecePos, cellSize, CellIndexDirection.EastWall);
        } else {
            piecePos = CellHelper.rotateCellTo(piecePos, cellSize, cellIndexDirection);
        }
        return piecePos;
    }

    public BlockWithMeta getWallBlock(ChunkCoordinates piecePos, int cellSize, int cellHeight, Random random,
            CellIndexDirection cellIndexDirection) {
        if (piecePos.posY > cellHeight - cellSize) {
            int slope = CellHelper.getSlopeIndex(piecePos, cellSize - piecePos.posZ - 3, 1,
                    BoundaryPair.createPair(0, cellSize * 2 - 8), cellHeight - cellSize / 3);
            int slopeBelow = CellHelper.getSlopeIndex(piecePos, cellSize - piecePos.posZ - 2, 1,
                    BoundaryPair.createPair(0, cellSize * 2 - 8), cellHeight - cellSize / 3);
            if (slope == 0) {
                if (slope != slopeBelow) {
                    return new BlockWithMeta(Block.stairsStoneBrick.blockID, getStairMeta(cellIndexDirection));
                } else {
                    return new BlockWithMeta(Block.stoneBrick.blockID, 5, getStairMeta(cellIndexDirection));
                }
            } else if (slope > 0) {
                return new BlockWithMeta(0);
            }
        }

        if (piecePos.posX != 0 && piecePos.posZ == cellSize * 4 / 10) {
            return new BlockWithMeta(Block.stoneBrick.blockID, 0);
        }

        if (piecePos.posY > cellHeight - 2 * cellSize) {
            int slope = CellHelper.getSlopeIndex(piecePos, cellSize - piecePos.posZ - 3, 1,
                    BoundaryPair.createPair(1, cellSize * 2 - 8), cellHeight - cellSize);
            int slopeBelow = CellHelper.getSlopeIndex(piecePos, cellSize - piecePos.posZ - 2, 1,
                    BoundaryPair.createPair(1, cellSize * 2 - 8), cellHeight - cellSize);
            if (slope == 0) {
                if (slope != slopeBelow) {
                    return new BlockWithMeta(Block.stairsStoneBrick.blockID, getStairMeta(cellIndexDirection));
                } else {
                    return new BlockWithMeta(Block.stoneBrick.blockID, 5, getStairMeta(cellIndexDirection));
                }
            }

            /* Arches */
            int topAarchSlope = CellHelper.getSlopeIndex(piecePos, cellSize - piecePos.posZ + 0, 1,
                    BoundaryPair.createPair(1, cellSize * 2), cellHeight - cellSize);
            int botAarchSlope = CellHelper.getSlopeIndex(piecePos, cellSize - piecePos.posZ + 1, 1,
                    BoundaryPair.createPair(1, cellSize * 2), cellHeight - cellSize);
            if ((topAarchSlope == 0 || botAarchSlope == 0) && piecePos.posX % 3 == 1
                    && piecePos.posZ > cellSize * 4 / 10) {
                if (piecePos.posX > 1) {
                    return new BlockWithMeta(Block.stairsStoneBrick.blockID, getArchStairMeta(cellIndexDirection,
                            topAarchSlope == 0 ? true : false));
                }
            }
        }

        /* Outer Wall */
        if (piecePos.posX == 1 && piecePos.posZ > cellSize * 4 / 10) {
            return new BlockWithMeta(Block.stoneBrick.blockID, 0);
        }

        if (piecePos.posY == 0 && piecePos.posX > 0 && piecePos.posZ > cellSize * 4 / 10) {
            if (piecePos.posZ == cellSize * 4 / 10 + 1) {
                return new BlockWithMeta(Block.cobblestone.blockID);
            } else {
                return new BlockWithMeta(Block.stoneBrick.blockID);
            }
        }

        /* Pews */
        if (piecePos.posY == 1 && piecePos.posX > 1 && piecePos.posX % 2 == 0) {
            if (piecePos.posZ == cellSize * 4 / 10 + 2) {
                return new BlockWithMeta(Block.stairsWoodOak.blockID, getPewStairMeta(cellIndexDirection));
            } else if (piecePos.posZ > cellSize * 4 / 10 + 2) {
                return new BlockWithMeta(Block.woodSingleSlab.blockID);
            }
        }
        return new BlockWithMeta(0);
    }

    private int getStairMeta(CellIndexDirection cellIndexDirection) {
        switch (cellIndexDirection) {
        case WestWall:
        case NorthEastCorner:
            return 0;
        case EastWall:
        case SouthWestCorner:
            return 1;
        case SouthWall:
        case NorthWestCorner:
            return 3;
        case NorthWall:
        case SouthEastCorner:
        default:
            return 2;
        }
    }

    private int getArchStairMeta(CellIndexDirection cellIndexDirection, boolean top) {
        switch (cellIndexDirection) {
        case NorthWall:
        case SouthEastCorner:
            return top ? 2 : 7;
        case SouthWall:
        case NorthWestCorner:
            return top ? 3 : 6;
        case WestWall:
        case NorthEastCorner:
            return top ? 0 : 5;
        case EastWall:
        case SouthWestCorner:
            return top ? 1 : 4;
        default:
            return 0;
        }
    }

    private int getPewStairMeta(CellIndexDirection cellIndexDirection) {
        switch (cellIndexDirection) {
        case NorthWall:
        case SouthEastCorner:
            return 3;
        case SouthWall:
        case NorthWestCorner:
            return 2;
        case WestWall:
        case NorthEastCorner:
            return 1;
        case EastWall:
        case SouthWestCorner:
            return 0;
        default:
            return 0;
        }
    }

    @Override
    public String getIdentifier() {
        return "BPCathedralHallwayEnd";
    }

    @Override
    public int getWeight() {
        return 0;
    }
}