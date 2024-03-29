package theBrewmaster.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.characters.BrewmasterCharacter;
import theBrewmaster.enums.CustomTags;

import static theBrewmaster.BrewmasterMod.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class VolatileBrew extends AbstractBrewmasterCard {
    
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = BrewmasterCharacter.Enums.ORANGE;
    
    private static final int COST = 1;
    
    public static final int[] DAMAGES = {8,12,16};
    public static final int[] UPGRADED_DAMAGES = {12,16,20};
    private int[] damageArray;
    
    // TEXT DECLARATION
    public static final String ID = BrewmasterMod.makeID(VolatileBrew.class.getSimpleName());
    public static final String IMG = makeCardPath(VolatileBrew.class.getSimpleName(), TYPE);

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    
    public VolatileBrew() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = 12;
        this.baseMagicNumber = this.magicNumber = 1;
        this.damageArray = DAMAGES;
        if (CardCrawlGame.dungeon != null && AbstractDungeon.player != null){
            this.magicNumber = AbstractDungeon.cardRandomRng.random(2);
        }
        
        tags.add(CustomTags.BREW);
    }
    @Override
    public void triggerWhenDrawn() {
        this.magicNumber = AbstractDungeon.cardRandomRng.random(2);
    }

    @Override
    public void applyPowers() {
        this.baseDamage = this.damage = damageArray[this.magicNumber];
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
    }
    public AbstractCard makeCopy(){
        AbstractCard ret = new VolatileBrew();
        ret.baseDamage = this.baseDamage;
        ret.damage = this.damage;
        return ret;
    }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.damageArray = UPGRADED_DAMAGES;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}