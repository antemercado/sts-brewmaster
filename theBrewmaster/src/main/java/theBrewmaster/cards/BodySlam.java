package theBrewmaster.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.characters.BrewmasterCharacter;

import static theBrewmaster.BrewmasterMod.makeDefaultCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;

@AutoAdd.Ignore
public class BodySlam extends AbstractBrewmasterCard {
    
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = BrewmasterCharacter.Enums.ORANGE;
    
    private static final int COST = 2;
    
    private static final int DAMAGE = 14;
    private static final int UPGRADE_PLUS_DMG = 2;
    
    private static final int MAGIC = 2;
    private static final int UPGRADE_PLUS_MAGIC = 1;
    
    // TEXT DECLARATION
    public static final String ID = BrewmasterMod.makeID(BodySlam.class.getSimpleName());
    public static final String IMG = makeDefaultCardPath(BodySlam.class.getSimpleName(), TYPE);
    
    public BodySlam() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = this.damage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = MAGIC;
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new ApplyPowerAction(m, p, new WeakPower(m, magicNumber, false)));
    }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}