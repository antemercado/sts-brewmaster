package theBrewmaster.cardmodifiers;

import com.megacrit.cardcrawl.cards.AbstractCard;

import basemod.abstracts.AbstractCardModifier;
import basemod.cardmods.EtherealMod;

public class ImpulseModifier extends EtherealMod{

    public ImpulseModifier() {
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return true;
    }

    @Override
    public boolean removeAtEndOfTurn(AbstractCard card) {
        return true;
    }

    @Override
    public void onRemove(AbstractCard card) {
        card.isEthereal = false;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ImpulseModifier();
    }
    
}
