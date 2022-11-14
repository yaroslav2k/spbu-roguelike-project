package lib;

import core.GameState;
import models.Player;
import models.Inventory;

// FIXME: Better naming?
public class GameStateChanger {
    public static void call(GameState gameState, Event event) {
        Logger.log("Event: " + event.getType().toString());
        Player player = gameState.getPlayer();

        switch (event.getType()) {
            case PLAYER_MOVE_UP:
                gameState.updateGameState(0, -1);

                break;
            case PLAYER_MOVE_DOWN:
                gameState.updateGameState(0, 1);

                break;
            case PLAYER_MOVE_LEFT:
                gameState.updateGameState(-1, 0);

                break;
            case PLAYER_MOVE_RIGHT:
                gameState.updateGameState(1, 0);

                break;
            case PLAYER_SELECT_ITEM:
                Inventory.getInventory().setSelectedStatus(true);

                break;
            case PLAYER_UNSELECT_ITEM:
                Inventory.getInventory().setSelectedStatus(false);

                break;
            case PLAYER_SELECT_NEXT_ITEM:
                Inventory.getInventory().selectNext();

                break;
            case PLAYER_SELECT_PREVIOUS_ITEM:
                Inventory.getInventory().selectPrevious();

                break;
            case PLAYER_EQUIP_ITEM:
                Inventory.getInventory().selectedItemChangeEquipStatus(player);

                break;
            case GAME_EXIT:
                Logger.log("Exiting..");
                System.exit(0);
            default:
                Logger.log("WARN", "Unknow event: " + event.getType().toString());
        }
    }
}
