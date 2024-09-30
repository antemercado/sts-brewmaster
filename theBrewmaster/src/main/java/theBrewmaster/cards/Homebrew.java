package theBrewmaster.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.actions.ApplyIntoxicationPower;
import theBrewmaster.actions.HomebrewAction;
import theBrewmaster.characters.BrewmasterCharacter;
import theBrewmaster.enums.CustomTags;
import theBrewmaster.powers.IntoxicationPower;

import static theBrewmaster.BrewmasterMod.makeCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

public class Homebrew extends AbstractBrewmasterCard {
    
    // STAT DECLARATION
    
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = BrewmasterCharacter.Enums.ORANGE;
    
    private static final int COST = 1;

    // TEXT DECLARATION
    public static final String ID = BrewmasterMod.makeID(Homebrew.class.getSimpleName());
    public static final String IMG = makeCardPath(Homebrew.class.getSimpleName(), TYPE);
    
    // Base Intoxication
    private static final int MISC = 8;
    
    // Intoxication Increase
    private static final int MAGIC2 = 2;
    private static final int UPGRADE_PLUS_MAGIC2 = 2;
    
    public Homebrew() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.misc = MISC;
        this.baseMagicNumber = this.magicNumber = MISC;
        this.baseMagicNumber2 = this.magicNumber2 = MAGIC2;

        tags.add(CustomTags.BREW);
    }

    // public void triggerWhenDrawn(){
    //     AbstractDungeon.actionManager.addToBottom(new HomebrewAction(this.uuid, this.magicNumber2));
    //     // this.magicNumber += this.magicNumber2;
    //     // applyPowers();
    // }

    public void onBattleStart(AbstractRoom room) {
        AbstractDungeon.actionManager.addToBottom(new HomebrewAction(this.uuid, this.magicNumber2));
    }
    
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            AbstractDungeon.actionManager.addToBottom(new ApplyIntoxicationPower(p, p, new IntoxicationPower(p, p, this.baseMagicNumber)));
        }

    public void applyPowers(){
        this.baseMagicNumber = this.misc;
        super.applyPowers();
        initializeDescription();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber2(UPGRADE_PLUS_MAGIC2);
            initializeDescription();
        }
    }
}