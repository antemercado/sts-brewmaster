package theBrewmaster.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.characters.BrewmasterCharacter;

import static theBrewmaster.BrewmasterMod.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.defect.DamageAllButOneEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ExplodingKeg extends AbstractBrewmasterCard {
    
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = BrewmasterCharacter.Enums.ORANGE;
    
    private static final int COST = 2;

    private static final int MAGIC = 15;
    private static final int DAMAGE = 5;
    private static final int UPGRADE_PLUS_DMG = 5;
    
    // TEXT DECLARATION
    public static final String ID = BrewmasterMod.makeID(ExplodingKeg.class.getSimpleName());
    public static final String IMG = makeCardPath(ExplodingKeg.class.getSimpleName(), TYPE);
    
    public ExplodingKeg() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.isMultiDamage = true;
    }

    // Thanks Alchyr
    @Override
    public void applyPowers() {
        this.isMultiDamage = false;
        int aoeDmg = this.baseDamage;
        this.baseDamage = this.baseMagicNumber;
        super.applyPowers();
        this.magicNumber = damage;
        isMagicNumberModified = isDamageModified;

        this.baseDamage = aoeDmg;
        isMultiDamage = true;
        super.applyPowers();
    }

    // Thanks Alchyr
    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        this.isMultiDamage = false;
        int aoeDmg = this.baseDamage;
        this.baseDamage = this.baseMagicNumber;
        super.calculateCardDamage(mo);
        this.magicNumber = damage;
        isMagicNumberModified = isDamageModified;

        this.baseDamage = aoeDmg;
        isMultiDamage = true;
        super.calculateCardDamage(mo);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, magicNumber, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        addToBot(new DamageAllButOneEnemyAction(p, m, multiDamage, damageType, AbstractGameAction.AttackEffect.FIRE));
    }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }
}