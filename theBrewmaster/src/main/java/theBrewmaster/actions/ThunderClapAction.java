package theBrewmaster.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ThunderClapAction extends AbstractGameAction{

    private AbstractPlayer p;
    private int damage;
    private int energyOnUse;
    private boolean freeToPlayOnce;
    private AttackEffect attackEffect = AttackEffect.LIGHTNING;

    public ThunderClapAction(AbstractPlayer p, int damage, int energyOnUse){
        this(p, damage, energyOnUse, false);
    }

    public ThunderClapAction(AbstractPlayer p, int damage, int energyOnUse, boolean freeToPlayOnce) {
        this.p = p;
        this.damage = damage;
        this.energyOnUse = energyOnUse;
    }

    @Override
    public void update() {
        int effect = EnergyPanel.totalCount;
        
        if (this.energyOnUse != -1)
            effect = this.energyOnUse;
        
        if (this.p.hasRelic("Chemical X")){
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (effect > 0){
            for (int i = 0; i < effect; i++){
                this.target = AbstractDungeon.getMonsters().getRandomMonster(null,true,AbstractDungeon.cardRandomRng);
                if (this.target != null)
                    addToTop(new DamageAction(this.target, new DamageInfo(p, this.damage), this.attackEffect));
            }
        }

        if (!this.freeToPlayOnce)
            this.p.energy.use(EnergyPanel.totalCount);
        
        this.isDone = true;

    }
    
}
