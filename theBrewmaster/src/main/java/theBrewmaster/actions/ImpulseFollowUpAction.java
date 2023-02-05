package theBrewmaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.helpers.CardModifierManager;
import theBrewmaster.cardmodifiers.ImpulseModifier;

public class ImpulseFollowUpAction extends AbstractGameAction{ 

    @Override
    public void update() {
        AbstractDungeon.actionManager.addToTop(new WaitAction(0.4f));
        tickDuration();
        if (this.isDone){
            for (AbstractCard c : DrawCardAction.drawnCards) {
                CardModifierManager.addModifier(c, new ImpulseModifier());
            }
        }  
    }
    
}
