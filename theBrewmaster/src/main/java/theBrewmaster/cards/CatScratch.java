package theBrewmaster.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.characters.BrewmasterCharacter;

import static theBrewmaster.BrewmasterMod.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CatScratch extends AbstractBrewmasterCard {
    
    // STAT DECLARATION
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = BrewmasterCharacter.Enums.ORANGE;
    
    private static final int COST = 0;
    
    private static final int DAMAGE = 4;
    private static final int UPGRADE_PLUS_DMG = 1;
    
    // TEXT DECLARATION
    public static final String ID = BrewmasterMod.makeID(CatScratch.class.getSimpleName());
    public static final String IMG = makeCardPath(CatScratch.class.getSimpleName(), TYPE);
    
    public CatScratch() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = DAMAGE;
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new ModifyDamageAction(this.uuid, this.damage));
        this.modifyCostForCombat(1);
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