package theBrewmaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.WallopEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import theBrewmaster.powers.IntoxicationPower;



public class HeavyDrinkAction extends AbstractGameAction{

    private static final int MULTIPLIER = 2;
    private DamageInfo damageInfo;

    public HeavyDrinkAction(AbstractCreature source, AbstractCreature target, DamageInfo damage) {
        this.source = source;
        this.target = target;
        this.damageInfo = damage;
    }

    @Override
    public void update() {

        if (shouldCancelAction()){
            this.isDone = true;
            return;
        }

        tickDuration();

        if (this.isDone){
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AbstractGameAction.AttackEffect.BLUNT_HEAVY, false));
    
            this.target.damage(this.damageInfo);
    
            if (this.target.lastDamageTaken > 0){
                addToTop(new ApplyIntoxicationPower(source, source,
                    new IntoxicationPower(this.source, this.source, this.target.lastDamageTaken * MULTIPLIER), this.target.lastDamageTaken * MULTIPLIER));
                if (this.target.hb != null){
                    addToTop(new VFXAction(new WallopEffect(this.target.lastDamageTaken, this.target.hb.cX, this.target.hb.cY)));
                }
            }
    
            if ((AbstractDungeon.getCurrRoom()).monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            } else {
                addToTop((AbstractGameAction)new WaitAction(0.1f));
            }
        }
    }
}
