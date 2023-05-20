package restaurant.util.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import restaurant.model.*;
import restaurant.util.json.load_templates.process.ProcessLog;
import restaurant.util.json.load_templates.process.ProcessLogsFileTemplate;
import restaurant.util.json.upload_templates.cooks.CooksFileTemplate;
import restaurant.util.json.upload_templates.dish_cards.DishCardsFileTemplate;
import restaurant.util.json.upload_templates.equipment.EquipmentFileTemplate;
import restaurant.util.json.upload_templates.equipment.EquipmentTypeFileTemplate;
import restaurant.util.json.upload_templates.menu.MenuDishFileTemplate;
import restaurant.util.json.upload_templates.product.IngredientFileTemplate;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

// Class for loading information from JSON-files
public class JsonHandler {
    protected static final Logger logger = LoggerFactory.getLogger(JsonHandler.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        var logFile = new File(Objects.requireNonNull(JsonHandler.class.getClassLoader().getResource("process_log.json")).getPath());
        try {
            objectMapper.writeValue(logFile, new ProcessLogsFileTemplate(new ArrayList<>()));
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Upload available recipes
     *
     * @return Available recipes
     */
    public static ArrayList<Recipe> upLoadRecipes() {
        ArrayList<Recipe> result = new ArrayList<>();
        try {
            var menuJson = objectMapper.readValue(JsonHandler.class.getClassLoader().getResource("menu_dishes.json"), MenuDishFileTemplate.class);
            var dishes = objectMapper.readValue(JsonHandler.class.getClassLoader().getResource("dish_cards.json"), DishCardsFileTemplate.class);
            for (var position : menuJson.getMenu_dishes()) {
                if (position.getMenu_dish_active()) {
                    var dish = dishes.getDish_cards().stream().filter(x -> Objects.equals(x.getId(), position.getMenu_dish_card())).findFirst();
                    if (dish.isPresent()) {
                        dish.get().setIngredients(dish.get().getOperations()
                                .stream()
                                .flatMap(listProducts -> listProducts.getIngredients().stream())
                                .collect(Collectors.toCollection(ArrayList::new)));
                        result.add(dish.get());
                    }
                }
            }
            if (dishes.getDish_cards().isEmpty()) {
                throw new RuntimeException("Wrong menu");
            }
        } catch (UnrecognizedPropertyException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Wrong file structure");
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Upload available equipment
     *
     * @return Available equipment
     */
    public static ArrayList<Equipment> upLoadEquipment() {
        ArrayList<Equipment> result = new ArrayList<>();
        try {
            var equipmentList = objectMapper.readValue(JsonHandler.class.getClassLoader().getResource("equipment.json"), EquipmentFileTemplate.class);
            var equipmentTypeList = objectMapper.readValue(JsonHandler.class.getClassLoader().getResource("equipment_type.json"), EquipmentTypeFileTemplate.class);
            for (var machine : equipmentList.getEquipment()) {
                if (machine.getEquip_active()) {
                    var equipment = equipmentTypeList.getEquipment_type().stream()
                            .filter(x -> Objects.equals(x.getType(), machine.getEquip_type()))
                            .findFirst();
                    equipment.ifPresent(x -> x.setName(machine.getEquip_name()));
                    equipment.ifPresent(result::add);
                }
            }
        } catch (UnrecognizedPropertyException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Wrong file structure");
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Upload available ingredients
     *
     * @return Available ingredients
     */
    public static ArrayList<Ingredient> upLoadIngredients() {
        ArrayList<Ingredient> result = new ArrayList<>();
        try {
            return objectMapper.readValue(JsonHandler.class.getClassLoader().getResource("products.json"), IngredientFileTemplate.class).getProducts();
        } catch (UnrecognizedPropertyException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Wrong file structure");
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Upload cooks
     *
     * @return Cooks
     */
    public static ArrayList<Cook> upLoadCooks() {
        ArrayList<Cook> result = new ArrayList<>();
        try {
            var cooks = objectMapper.readValue(JsonHandler.class.getClassLoader().getResource("cooks.json"), CooksFileTemplate.class);
            for (var cook : cooks.getCooks()) {
                if (cook.getIsActive()) {
                    result.add(cook);
                }
            }
        } catch (UnrecognizedPropertyException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Wrong file structure");
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Add process log to resources
     *
     * @param process Process log
     */
    public static void addProcessLog(ProcessLog process) {
        try {
            var logs = objectMapper.readValue(JsonHandler.class.getClassLoader().getResource("process_log.json"), ProcessLogsFileTemplate.class);
            var logFile = new File(Objects.requireNonNull(JsonHandler.class.getClassLoader().getResource("process_log.json")).getPath());
            ArrayList<ProcessLog> result = new ArrayList<>(logs.getProcess_log());
            process.setId(logs.getProcess_log().size() + 1L);
            result.add(process);
            objectMapper.writeValue(logFile, new ProcessLogsFileTemplate(result));

        } catch (UnrecognizedPropertyException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("Wrong file structure");
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
