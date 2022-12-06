package repositories;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import models.*;
import models.mobs.*;


public class MapRepository extends Repository {
    private Level[] levels;

    @Override
    String getDatasourceFilepath() {
        return "src/main/java/config/maps.yaml";
    }

    /**
    * This method is used to retrieve levels.
    * @return Level[] Returns retrieved levels.
    */
    // FIXME: getMap() -> getMaps()
    public Level[] getMap() {
        if (this.levels != null) { return this.levels; }

        ArrayList<LinkedHashMap> levels = (ArrayList<LinkedHashMap>) data.get("levels");
        Level[] targetLevels = new Level[levels.size()];
        int levelIndex = 0;
        for (LinkedHashMap level : levels) {
            Player player = null;
            ArrayList<LinkedHashMap> rooms = (ArrayList<LinkedHashMap>) level.get("rooms");
            Room[] targetRooms = new Room[rooms.size()];
            int roomIndex = 0;

            LinkedHashMap playerData = (LinkedHashMap) level.get("player");
            if (playerData != null) {
                player = new Player((Integer) playerData.get("x"), (Integer) playerData.get("y"));
            }

            for (LinkedHashMap room : rooms) {
                targetRooms[roomIndex++] = new Room(
                    (Boolean) room.get("visible"),
                    (Integer) room.get("x"),
                    (Integer) room.get("y"),
                    buildGameObjects((ArrayList<LinkedHashMap>) room.get("objects")),
                    (Integer) room.get("size")
                );
            }
            targetLevels[levelIndex++] = new Level(
                targetRooms,
                player
            );
        }

        this.levels = targetLevels;

        return targetLevels;
    }

    private List<GameObject> buildGameObjects(ArrayList<LinkedHashMap> data) {
        List<GameObject> gameObjects = new ArrayList<GameObject>();

        for (LinkedHashMap data_entry : data) {
            gameObjects.add(buildGameObject(data_entry));
        }

        return gameObjects;
    }

    private GameObject buildGameObject(LinkedHashMap data) {
        String type = (String) data.get("type");

        switch (type) {
            case "door":
                return new Door(
                    (Integer) data.get("x"),
                    (Integer) data.get("y"),
                    (Integer) data.get("target_x"),
                    (Integer) data.get("target_y"),
                    (Integer) data.get("target_room")
                );
            case "item":
                return new Item(
                    (Integer) data.get("x"),
                    (Integer) data.get("y"),
                    (String) data.get("name")
                );
            case "mob":
                Mob mob = new Mob(
                    (Integer) data.get("x"),
                    (Integer) data.get("y"),
                    (Integer) data.get("health"),
                    (Integer) data.get("power")
                );
                mob.setMobBehavior((String) data.get("behavior"));
                return mob;
            default:
                throw new IllegalArgumentException();
        }
    }
}
