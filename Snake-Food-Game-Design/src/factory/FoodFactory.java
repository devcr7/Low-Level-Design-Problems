package factory;

import enums.FoodType;
import model.BonusFood;
import model.FoodItem;
import model.NormalFood;


public class FoodFactory {
    public static FoodItem createFood(int[] position, FoodType foodType) {
        return switch (foodType) {
            case NORMAL -> new NormalFood(position[0], position[1]);
            case BONUS -> new BonusFood(position[0], position[1]);
        };
    }
}
