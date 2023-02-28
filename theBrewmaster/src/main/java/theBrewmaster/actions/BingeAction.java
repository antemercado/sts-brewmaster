package theBrewmaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import theBrewmaster.powers.IntoxicationPower;

public class BingeAction extends AbstractGameAction{

    private AbstractPlayer p;
    private int energy;
    private boolean freeToPlayOnce;
    private int magicNumber;

    public BingeAction(AbstractPlayer p, int block, int magicNumber, int energyOnUse, boolean freeToPlayOnce) {
        this.p = p;
        this.amount = block;
        this.magicNumber = magicNumber;
        this.energy = energyOnUse;
        this.freeToPlayOnce = freeToPlayOnce;
    }

    @Override
    public void update() {
        
        if (this.p.hasRelic("Chemical X")){
            this.energy += 2;
            this.p.getRelic("Chemical X").flash();
        }

        for (int i = 0; i < this.energy; i++){
            addToBot(new ApplyIntoxicationPower(p, p, new IntoxicationPower(this.p, this.p, this.magicNumber)));
            addToBot(new GainBlockAction(this.p, this.p, this.amount));
        }

        if (!this.freeToPlayOnce)
            this.p.energy.use(EnergyPanel.totalCount);

        this.isDone = true;
    }
    
}
