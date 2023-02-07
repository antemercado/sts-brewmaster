package theBrewmaster.events;

import static theBrewmaster.BrewmasterMod.makeEventPath;

import java.util.ArrayList;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.events.GenericEventDialog;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.AbstractPotion.PotionRarity;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import theBrewmaster.BrewmasterMod;
import theBrewmaster.cards.AdmiralsBrew;
import theBrewmaster.cards.CinderBrew;
import theBrewmaster.cards.PerplexingBrew;
import theBrewmaster.cards.PurifyingBrew;
import theBrewmaster.cards.StoneBrew;
import theBrewmaster.cards.TwistedBrew;
import theBrewmaster.cards.VolatileBrew;
import theBrewmaster.characters.BrewmasterCharacter;
import theBrewmaster.relics.BeerSteinRelic;
import theBrewmaster.relics.GiantKegRelic;

public class DistilleryRoomEvent extends AbstractImageEvent{

    public static final String ID = BrewmasterMod.makeID(DistilleryRoomEvent.class.getName());
    private static final EventStrings eventStrings = CardCrawlGame.languagePack.getEventString(ID);
    
    private static final String NAME = eventStrings.NAME;
    private static final String[] DESCRIPTIONS = eventStrings.DESCRIPTIONS;
    private static final String[] OPTIONS = eventStrings.OPTIONS;
    public static final String IMG = makeEventPath("IdentityCrisisEvent.png");

    private int screenNum = 0;
    private boolean hasBrewmasterRelic = (AbstractDungeon.player.hasRelic(BeerSteinRelic.ID) || AbstractDungeon.player.hasRelic(GiantKegRelic.ID));

    public DistilleryRoomEvent() {
        super(NAME, DESCRIPTIONS[0], IMG);
        //TODO Auto-generated constructor stub
    }

    @Override
    protected void buttonEffect(int i) {
        switch(screenNum){
            case 0:
                switch (i){
                    case 0: // Brewmaster Relics Only: Gain 100 Intoxication
                        if (!this.hasBrewmasterRelic){
                            return;
                        }
                        AbstractRelic curRelic;
                        if (AbstractDungeon.player.hasRelic(BeerSteinRelic.ID)){
                            curRelic = AbstractDungeon.player.getRelic(BeerSteinRelic.ID);
                        } else {
                            curRelic = AbstractDungeon.player.getRelic(GiantKegRelic.ID);
                        }

                        if (curRelic.counter < 51){
                            curRelic.counter = 100;
                        } else {
                            curRelic.counter += 50;
                        }

                        this.screenNum = 1;
                        break;

                    case 1: // Add a random Brew card
                        CardGroup brews = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

                        AbstractCard cb = new CinderBrew();
                        cb.upgrade();
                        brews.addToBottom(cb);
                        brews.addToBottom(new TwistedBrew());
                        brews.addToBottom(new VolatileBrew());
                        brews.addToBottom(new StoneBrew());
                        brews.addToBottom(new PurifyingBrew());
                        brews.addToBottom(new PerplexingBrew());
                        brews.addToBottom(new AdmiralsBrew());

                        AbstractCard cardReward = brews.getRandomCard(true);

                        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(cardReward, Settings.WIDTH / 2.0f, Settings.HEIGHT / 2.0f));

                        this.screenNum = 1;
                        break;
                    case 2: // Add a random rare potion.
                        GenericEventDialog.hide();
                        AbstractDungeon.getCurrRoom().rewards.clear();

                        ArrayList<AbstractPotion> potionList = PotionHelper.getPotionsByRarity(PotionRarity.RARE);
                        int p = AbstractDungeon.potionRng.random(potionList.size() - 1);

                        AbstractDungeon.getCurrRoom().rewards.add(new RewardItem(potionList.get(p)));
                        AbstractDungeon.combatRewardScreen.open();
                        logMetric(ID, "Got Potion");
                        
                        this.screenNum = 1;
                        break;
                }
            case 1:
                switch (i){
                    case 0:
                        openMap();
                        break;
                }
        }
        
    }
    
}
