package theBrewmaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

import theBrewmaster.powers.IntoxicationPower;

public class PurifyingBrewAction extends AbstractGameAction{

    private boolean includeCurses;

    public PurifyingBrewAction(AbstractPlayer p, int amount, boolean includeCurses) {
        
        this.target = p;
        this.source = p;
        this.amount = amount;
        this.includeCurses = includeCurses;
    }

    @Override
    public void update() {
        addToBot(new DiscardAction(target, source, 1, false));
        for (AbstractCard c: AbstractDungeon.handCardSelectScreen.selectedCards.group){
            if (c.type.equals(CardType.STATUS) || (this.includeCurses && c.type.equals(CardType.CURSE))){
                addToBot(new ApplyPowerAction(target, target, new IntoxicationPower(target, source, amount), amount));
            }
        }
        this.isDone = true;
    }
    
}
