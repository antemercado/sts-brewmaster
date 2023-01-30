package theBrewmaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import theBrewmaster.BrewmasterMod;
import theBrewmaster.enums.CustomTags;

public class FormulationAction extends AbstractGameAction{
    
    public static final String ID = BrewmasterMod.makeID(FormulationAction.class.getSimpleName());

    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    private static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer p;

    public FormulationAction(AbstractPlayer p, int discardAmount) {
        this.source = p;
        this.target = p;
        this.p = p;
        this.amount = discardAmount;
    }

    @Override
    public void update() {
        AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);
        for (AbstractCard c: AbstractDungeon.handCardSelectScreen.selectedCards.group){
            if (c.hasTag(CustomTags.BREW)){
                addToBot(new ObtainPotionAction(AbstractDungeon.returnRandomPotion(true)));
            }
            this.p.hand.moveToExhaustPile(c);
        }
        this.isDone = true;
    }
    
}
