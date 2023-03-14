package theBrewmaster.cards.microbrews;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.cards.AbstractBrewmasterCard;
import theBrewmaster.characters.BrewmasterCharacter;
import theBrewmaster.enums.CustomTags;
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
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

@AutoAdd.Ignore
public class CinderMicro extends AbstractBrewmasterCard {
    
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.SPECIAL;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = CardColor.COLORLESS;
    
    private static final int COST = 0;
    
    private static final int MAGIC = 3;
    private static final int UPGRADE_PLUS_MAGIC = 1;
    
    // TEXT DECLARATION
    public static final String ID = BrewmasterMod.makeID(CinderMicro.class.getSimpleName());
    public static final String IMG = makeCardPath(CinderMicro.class.getSimpleName(), TYPE);
    
    public CinderMicro() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = MAGIC;
        this.exhaust = true;
        this.selfRetain = true;

        tags.add(CustomTags.BREW);
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new DrenchedPower(m, p, magicNumber), magicNumber));
    }
    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}