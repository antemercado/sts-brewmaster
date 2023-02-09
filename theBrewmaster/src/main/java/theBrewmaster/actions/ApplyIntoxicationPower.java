package theBrewmaster.actions;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import theBrewmaster.powers.IntoxicationPower;
import theBrewmaster.powers.NoIntoxicationPower;

public class ApplyIntoxicationPower extends ApplyPowerAction{

    public ApplyIntoxicationPower(AbstractCreature target, AbstractCreature source, IntoxicationPower powerToApply, int stackAmount) {
        super(target, source, powerToApply, stackAmount);
    }

    public ApplyIntoxicationPower(AbstractCreature target, AbstractCreature source, IntoxicationPower powerToApply){
        super(target, target, powerToApply);
    }

    @Override
    public void update() {
        if (this.target.hasPower(NoIntoxicationPower.POWER_ID)){
            this.isDone = true;
            return;
        }
        super.update();
    }
}
