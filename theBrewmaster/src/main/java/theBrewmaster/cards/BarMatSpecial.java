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
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BarMatSpecial extends AbstractBrewmasterCard {
    
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = BrewmasterCharacter.Enums.ORANGE;
    
    private static final int COST = 1;
    
    private static final int DAMAGE = 5;
    private static final int UPGRADE_PLUS_DAMAGE = 3;

    private static final int MAGIC = 0;

    
    // TEXT DECLARATION
    public static final String ID = BrewmasterMod.makeID(BarMatSpecial.class.getSimpleName());
    public static final String IMG = makeCardPath(BarMatSpecial.class.getSimpleName(), TYPE);

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    
    public BarMatSpecial() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = DAMAGE;
        this.baseMagicNumber = 0;
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AttackEffect effect = AbstractGameAction.AttackEffect.BLUNT_LIGHT;

        for (int i = 0; i < BrewmasterMod.brewCardsPlayedThisTurn; i++){
            addToBot(new DamageAction(m, new DamageInfo(p, this.baseDamage, damageTypeForTurn), effect));
        }

    }

    // public void applyPowers() {
    //     if (BrewmasterMod.brewCardsPlayedThisTurn > 0){
    //         this.finalDamage = BrewmasterMod.brewCardsPlayedThisTurn * this.baseDamage;
    //         super.applyPowers();
    //         this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
    //         initializeDescription();
    //     }
    // }

    public void applyPowers() {
        if (BrewmasterMod.brewCardsPlayedThisTurn > 0){
            this.magicNumber = BrewmasterMod.brewCardsPlayedThisTurn;
            super.applyPowers();
            this.rawDescription = DESCRIPTION + EXTENDED_DESCRIPTION[0];
            initializeDescription();
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (BrewmasterMod.brewCardsPlayedThisTurn > 0){
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        }
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            initializeDescription();
        }
    }
}