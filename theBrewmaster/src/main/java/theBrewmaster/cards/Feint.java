package theBrewmaster.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.characters.BrewmasterCharacter;

import static theBrewmaster.BrewmasterMod.makeDefaultCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.PaperCrane;

public class Feint extends AbstractBrewmasterCard {
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = BrewmasterCharacter.Enums.ORANGE;

    private static final int COST = 2;

    private static final int DAMAGE = 12;
    private static final int UPGRADE_PLUS_DMG = 4;

    private static final float WEAKNESS_EFFECT = 0.25f;

    // TEXT DECLARATION
    public static final String ID = BrewmasterMod.makeID(Feint.class.getSimpleName());
    public static final String IMG = makeDefaultCardPath(Feint.class.getSimpleName(), TYPE);

    public Feint() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = DAMAGE;
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    @Override
    public void applyPowers() {
        AbstractPower weak = AbstractDungeon.player.getPower("Weakened");
        if (weak != null){
            this.baseDamage = (int)((this.baseDamage / (1 - WEAKNESS_EFFECT)) * (WEAKNESS_EFFECT + 1));
        }

        super.applyPowers();

        if (weak != null){
            this.baseDamage = (int)((this.baseDamage / (WEAKNESS_EFFECT + 1)) * (1 - WEAKNESS_EFFECT));
        }
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        AbstractPower weak = AbstractDungeon.player.getPower("Weakened");
        if (weak != null){
            this.baseDamage = (int)((this.baseDamage / (1 - WEAKNESS_EFFECT)) * (WEAKNESS_EFFECT + 1));
        }

        super.calculateCardDamage(mo);

        if (weak != null){
            this.baseDamage = (int)((this.baseDamage / (WEAKNESS_EFFECT + 1)) * (1 - WEAKNESS_EFFECT));
        }
    }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}