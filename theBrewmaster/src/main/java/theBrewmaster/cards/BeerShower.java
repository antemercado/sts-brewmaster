package theBrewmaster.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.characters.BrewmasterCharacter;
import theBrewmaster.powers.DrenchedPower;

import static theBrewmaster.BrewmasterMod.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class BeerShower extends AbstractBrewmasterCard {
    
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = BrewmasterCharacter.Enums.ORANGE;
    
    private static final int COST = 2;
    
    private static final int MAGIC = 4;
    private static final int MAGIC2 = 1;
    private static final int UPGRADE_PLUS_MAGIC = 2;
    private static final int UPGRADE_PLUS_MAGIC2 = 1;
    
    // TEXT DECLARATION
    public static final String ID = BrewmasterMod.makeID(BeerShower.class.getSimpleName());
    public static final String IMG = makeCardPath(BeerShower.class.getSimpleName(), TYPE);
    
    public BeerShower() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.baseMagicNumber2 = this.magicNumber2 = MAGIC2;
        this.exhaust = true;
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo: AbstractDungeon.getMonsters().monsters){
            addToBot(new ApplyPowerAction(mo, p, new DrenchedPower(mo, p, magicNumber), this.magicNumber));
            addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, magicNumber2, false), this.magicNumber2));
        }
    }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            upgradeMagicNumber2(UPGRADE_PLUS_MAGIC2);
            initializeDescription();
        }
    }
}