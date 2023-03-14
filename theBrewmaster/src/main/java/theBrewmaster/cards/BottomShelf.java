package theBrewmaster.cards;

import basemod.AutoAdd;
import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModCardTags;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import theBrewmaster.BrewmasterMod;
import theBrewmaster.characters.BrewmasterCharacter;

import static theBrewmaster.BrewmasterMod.makeDefaultCardPath;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class BottomShelf extends AbstractBrewmasterCard{
    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = BrewmasterCharacter.Enums.ORANGE;

    private static final int COST = 1;

    private static final int BLOCK = 6;

    private static final int MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    // TEXT DECLARATION
    public static final String ID = BrewmasterMod.makeID(BottomShelf.class.getSimpleName());
    public static final String IMG = makeDefaultCardPath(BottomShelf.class.getSimpleName(), TYPE);

    public BottomShelf() { 
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.baseBlock = this.block = BLOCK;
        this.baseMagicNumber = this.magicNumber = MAGIC;
        CardGroup microbrews = BrewmasterMod.getMicrobrews();
        AbstractCard[] previews = new AbstractCard[microbrews.size()];
        previews = microbrews.group.toArray(previews);
        MultiCardPreview.add(this, previews);
    }
    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        for (int i = 0; i < this.magicNumber; i++){
            addToBot(new MakeTempCardInHandAction(BrewmasterMod.getMicrobrews().getRandomCard(true)));
        }
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