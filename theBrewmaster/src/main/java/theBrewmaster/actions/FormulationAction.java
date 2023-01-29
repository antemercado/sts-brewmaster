package theBrewmaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import theBrewmaster.enums.CustomTags;

public class FormulationAction extends AbstractGameAction{

    public FormulationAction(AbstractPlayer p, int discardAmount) {
        this.source = p;
        this.target = p;
        this.amount = discardAmount;
    }

    @Override
    public void update() {
        addToBot(new ExhaustAction(target, source, amount, false));
        for (AbstractCard c: AbstractDungeon.handCardSelectScreen.selectedCards.group){
            if (c.hasTag(CustomTags.BREW)){
                addToBot(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
            }
        }
    }
    
}
