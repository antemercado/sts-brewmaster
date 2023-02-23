package theBrewmaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

import theBrewmaster.powers.IntoxicationPower;

public class BottleSmashAction extends AbstractGameAction{

    private DamageInfo info;

    public BottleSmashAction(AbstractCreature target, AbstractCreature source, DamageInfo info, int amount) {
        this.target = target;
        this.source = source;
        this.info = info;
        this.amount = amount;
    }

    @Override
    public void update() {
        addToBot(new ApplyIntoxicationPower(this.source, this.source, new IntoxicationPower(this.source, this.source, this.amount), this.amount));
        addToBot(new DamageAction(this.target, this.info, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        this.isDone = true;
    }
    
}
