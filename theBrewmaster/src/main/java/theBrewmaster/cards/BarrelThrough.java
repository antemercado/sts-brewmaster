package theBrewmaster.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.characters.BrewmasterCharacter;

import static theBrewmaster.BrewmasterMod.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@AutoAdd.Ignore
public class BarrelThrough extends AbstractBrewmasterCard {
    
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = BrewmasterCharacter.Enums.ORANGE;
    
    private static final int COST = 2;
    
    private static final int DAMAGE = 10;
    private static final int UPGRADE_PLUS_DMG = 3;

    private static final int BLOCK = 6;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    
    // TEXT DECLARATION
    public static final String ID = BrewmasterMod.makeID(BarrelThrough.class.getSimpleName());
    public static final String IMG = makeCardPath(BarrelThrough.class.getSimpleName(), TYPE);
    
    public BarrelThrough() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = DAMAGE;
        this.baseBlock = this.block = BLOCK;
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, damage, damageTypeForTurn, AttackEffect.BLUNT_LIGHT));
        addToBot(new GainBlockAction(p, p, block));
    }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}