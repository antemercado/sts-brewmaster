package theBrewmaster.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import theBrewmaster.DefaultMod;
import theBrewmaster.characters.TheBrewmaster;
import theBrewmaster.powers.IntoxicationPower;

import static theBrewmaster.DefaultMod.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;

public class CrazyBrew extends AbstractDynamicCard {
    // TEXT DECLARATION
    public static final String ID = DefaultMod.makeID(CrazyBrew.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheBrewmaster.Enums.COLOR_GRAY;

    private static final int COST = 1;

    private static final int MAGIC = 10;
    private static final int UPGRADE_PLUS_MAGIC = 5;

    private static final int MAGIC2 = 1;
    private static final int UPGRADE_PLUS_MAGIC2 = 1;

    public CrazyBrew() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = magicNumber = MAGIC;
        this.baseMagicNumber2 = magicNumber2 = MAGIC2;
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new IntoxicationPower(p, p, this.baseMagicNumber)));
        addToBot(new ApplyPowerAction(p, p, new EnergizedPower(p, MAGIC2)));
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