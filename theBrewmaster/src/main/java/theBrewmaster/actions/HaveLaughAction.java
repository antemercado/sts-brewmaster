package theBrewmaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.UIStrings;

import theBrewmaster.BrewmasterMod;

public class HaveLaughAction extends AbstractGameAction{

    public static final String ID = BrewmasterMod.makeID(HaveLaughAction.class.getSimpleName());
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ID);
    public static final String TEXT[] = uiStrings.TEXT;

    private AbstractPlayer p = AbstractDungeon.player;
    private CardGroup hand;

    public HaveLaughAction(int energyAmount) {
        this.amount = energyAmount;

        this.target = this.source = this.p;
        this.hand = this.p.hand;
    }

    @Override
    public void update() {
        
        addToBot(new ExhaustAction(1, false));
        for (AbstractCard c: hand.group){
            addToBot(new ReduceCostForTurnAction(c, amount));
        }

        this.isDone = true;
    }
    
}
