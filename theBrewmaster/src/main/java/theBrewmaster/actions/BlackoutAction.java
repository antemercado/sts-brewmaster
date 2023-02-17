package theBrewmaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import theBrewmaster.cards.status.WoozyStatus;
import theBrewmaster.powers.IntoxicationPower;

public class BlackoutAction extends AbstractGameAction{

    private int intoxAmount;

    public BlackoutAction(int woozyAmount, int intoxAmount) {
        this.amount = woozyAmount;
        this.intoxAmount = intoxAmount;
    }

    @Override
    public void update() {

        AbstractPlayer p = AbstractDungeon.player;
        int playerIntox = 0;

        if (p.hasPower(IntoxicationPower.POWER_ID)){
            playerIntox = p.getPower(IntoxicationPower.POWER_ID).amount;
        }
        if (playerIntox < this.intoxAmount){
            addToBot(new ApplyIntoxicationPower(p, p, new IntoxicationPower(p, p, this.intoxAmount - playerIntox)));
        }
        addToBot(new MakeTempCardInDiscardAction(new WoozyStatus(), this.amount));
        this.isDone = true;
    }
    
}
