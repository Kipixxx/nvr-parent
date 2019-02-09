package fr.novaria.skygrid.api.chest;

import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.valueOf;

public class ChestQuantity {

    private final int quantity;

    public ChestQuantity(int quantity) {
        checkArgument(quantity > 0, "quantity must be positive");
        this.quantity = quantity;
    }

    public int getRandomCount(Random random) {
        return random.nextInt(quantity) + 1;
    }

    @Override
    public int hashCode() {
        return quantity;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == this) {
            return true;
        }

        if (!(obj instanceof ChestQuantity)) {
            return false;
        }

        final ChestQuantity other = (ChestQuantity) obj;
        return quantity == other.quantity;
    }

    @Override
    public String toString() {
        return valueOf(quantity);
    }
}
