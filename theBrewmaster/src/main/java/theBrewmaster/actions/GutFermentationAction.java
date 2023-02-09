package theBrewmaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import theBrewmaster.powers.IntoxicationPower;
import theBrewmaster.relics.LouseLiverRelic;

public class GutFermentationAction extends AbstractGameAction{

    private final AbstractPlayer p = AbstractDungeon.player;
    private AbstractPower intoxPower;
    private boolean upgraded;
    private AbstractCard card;

    public GutFermentationAction(AbstractCard card, boolean isUpgraded) {
        this.card = card;
        this.upgraded = isUpgraded;
    }

    @Override
    public void update() {
        if (this.p.hasPower(IntoxicationPower.POWER_ID)){
            this.intoxPower = p.getPower(IntoxicationPower.POWER_ID);
            int currIntox = this.intoxPower.amount;
            addToBot(new ApplyIntoxicationPower(p, p, new IntoxicationPower(p, p, currIntox), currIntox));
        }
        if (this.intoxPower == null){
            this.isDone = true;
            return;
        }
        int newIntox = intoxPower.amount * 2;
        if (upgraded && (newIntox >= IntoxicationPower.INTOX_THRESHOLD || 
            (newIntox >= IntoxicationPower.INTOX_THRESHOLD_RELIC && this.p.hasRelic(LouseLiverRelic.ID)))){
                p.limbo.moveToExhaustPile(card);
        }
        this.isDone = true;
    }
    
}
