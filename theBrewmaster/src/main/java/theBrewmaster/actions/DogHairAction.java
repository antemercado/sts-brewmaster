package theBrewmaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower.PowerType;

public class DogHairAction extends AbstractGameAction{

    public DogHairAction(AbstractCreature target, AbstractCreature source, int amount) {
        this.target = target;
        this.source = source;
        this.amount = amount;
    }

    @Override
    public void update() {
        for (AbstractPower po: this.target.powers){
            if (po.type.equals(PowerType.DEBUFF)){
                if (po.amount < 0){
                    addToTop(new ApplyPowerAction(target, target, po, amount));
                }
                if (po.amount > 0){
                    addToTop(new ReducePowerAction(target, target, po, amount));
                }
                if (po.amount == 0){
                    addToTop(new RemoveSpecificPowerAction(this.target, this.target, po));
                }
            }
        }
        this.isDone = true;
    }
    
}
