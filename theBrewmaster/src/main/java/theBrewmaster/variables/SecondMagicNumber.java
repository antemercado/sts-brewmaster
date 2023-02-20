package theBrewmaster.variables;

import basemod.abstracts.DynamicVariable;
import theBrewmaster.cards.AbstractBrewmasterCard;

import static theBrewmaster.BrewmasterMod.makeID;

import com.megacrit.cardcrawl.cards.AbstractCard;

public class SecondMagicNumber extends DynamicVariable {

    //For in-depth comments, check the other variable(DefaultCustomVariable). It's nearly identical.

    @Override
    public String key() {
        return makeID("M2");
        // This is what you put between "!!" in your card strings to actually display the number.
        // You can name this anything (no spaces), but please pre-phase it with your mod name as otherwise mod conflicts can occur.
        // Remember, we're using makeID so it automatically puts "theDefault:" (or, your id) before the name.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractBrewmasterCard) card).isMagicNumber2Modified;

    }

    @Override
    public int value(AbstractCard card) {
        return ((AbstractBrewmasterCard) card).magicNumber2;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractBrewmasterCard) card).baseMagicNumber2;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractBrewmasterCard) card).upgradedMagicNumber2;
    }
}