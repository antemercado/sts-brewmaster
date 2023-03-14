package theBrewmaster.cards.microbrews;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.cards.AbstractBrewmasterCard;
import theBrewmaster.characters.BrewmasterCharacter;
import theBrewmaster.enums.CustomTags;
import theBrewmaster.powers.AdmiralsBrewPower;

import static theBrewmaster.BrewmasterMod.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

@AutoAdd.Ignore
public class DefendMicro extends AbstractBrewmasterCard {
    
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;
    
    private static final int COST = 0;
    
    private static final int BLOCK = 4;
    private static final int UPGRADE_PLUS_BLOCK = 2;
    
    // TEXT DECLARATION
    public static final String ID = BrewmasterMod.makeID(DefendMicro.class.getSimpleName());
    public static final String IMG = makeCardPath(DefendMicro.class.getSimpleName(), TYPE);

    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    public DefendMicro() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);

        this.baseBlock = this.block = BLOCK;
        this.exhaust = true;
        this.selfRetain = true;

        tags.add(CustomTags.BREW);
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, m, this.block));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}