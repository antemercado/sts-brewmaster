package theBrewmaster;

import basemod.*;
import basemod.eventUtil.AddEventParams;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.DynamicTextBlocks;
import theBrewmaster.cards.*;
import theBrewmaster.cards.microbrews.DefendMicro;
import theBrewmaster.cards.microbrews.CinderMicro;
import theBrewmaster.cards.microbrews.VolatileMicro;
import theBrewmaster.cards.microbrews.IntoxMicro;
import theBrewmaster.cards.microbrews.MedicalMicro;
import theBrewmaster.characters.BrewmasterCharacter;
import theBrewmaster.enums.CustomTags;
import theBrewmaster.events.IdentityCrisisEvent;
import theBrewmaster.potions.FermentedTea;
import theBrewmaster.potions.InertIncendiary;
import theBrewmaster.potions.ShiftingAle;
import theBrewmaster.relics.BeerSteinRelic;
import theBrewmaster.relics.EthanolFlaskRelic;
import theBrewmaster.relics.CozyBeerRelic;
import theBrewmaster.relics.GiantKegRelic;
import theBrewmaster.relics.LouseLiverRelic;
import theBrewmaster.relics.MiniFridgeRelic;
import theBrewmaster.relics.OctarineCupRelic;
import theBrewmaster.relics.SickBagRelic;
import theBrewmaster.relics.SpiritHelmetRelic;
import theBrewmaster.stances.IntoxicatedStance;
import theBrewmaster.util.IDCheckDontTouchPls;
import theBrewmaster.util.TextureLoader;
import theBrewmaster.variables.DefaultCustomVariable;
import theBrewmaster.variables.SecondMagicNumber;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheCity;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
@SpireInitializer
public class BrewmasterMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        OnStartBattleSubscriber,
        OnCardUseSubscriber,
        OnPlayerTurnStartSubscriber {
    public static final Logger logger = LogManager.getLogger(BrewmasterMod.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties theBrewmasterDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = false; // The boolean we'll be setting on/off (true/false)

    public static int beerSteinAmount = 75;
    public static int intoxDecayAmount = 15;
    public static int intoxThreshAmount = 100;

    // =============== INPUT TEXTURE LOCATION =================
    
    // Colors (RGB)
    // Character Color
    public static final Color BREWMASTER_ORANGE = CardHelper.getColor(235.0f, 124.0f, 33.0f);
  
    // Card backgrounds
    private static final String ATTACK_BREWMASTER_ORANGE = "theBrewmasterResources/images/512/bg_attack_default_orange.png";
    private static final String SKILL_BREWMASTER_ORANGE = "theBrewmasterResources/images/512/bg_skill_default_orange.png";
    private static final String POWER_BREWMASTER_ORANGE = "theBrewmasterResources/images/512/bg_power_default_orange.png";
    
    private static final String ENERGY_ORB_BREWMASTER_ORANGE = "theBrewmasterResources/images/512/card_default_orange_orb.png";
    private static final String CARD_ENERGY_ORB = "theBrewmasterResources/images/512/card_small_orb.png";
    
    private static final String ATTACK_BREWMASTER_ORANGE_PORTRAIT = "theBrewmasterResources/images/1024/bg_attack_default_orange.png";
    private static final String SKILL_BREWMASTER_ORANGE_PORTRAIT = "theBrewmasterResources/images/1024/bg_skill_default_orange.png";
    private static final String POWER_BREWMASTER_ORANGE_PORTRAIT = "theBrewmasterResources/images/1024/bg_power_default_orange.png";
    private static final String ENERGY_ORB_BREWMASTER_ORANGE_PORTRAIT = "theBrewmasterResources/images/1024/card_default_orange_orb.png";
    
    // Character assets
    private static final String BREWMASTER_BUTTON = "theBrewmasterResources/images/charSelect/CharacterButton.png";
    private static final String BREWMASTER_PORTRAIT = "theBrewmasterResources/images/charSelect/CharacterPortraitBG.png";
    public static final String BREWMASTER_SHOULDER_1 = "theBrewmasterResources/images/char/brewmasterAnimation/shoulder.png";
    public static final String BREWMASTER_SHOULDER_2 = "theBrewmasterResources/images/char/brewmasterAnimation/shoulder2.png";
    public static final String BREWMASTER_CORPSE = "theBrewmasterResources/images/char/brewmasterAnimation/corpse.png";
    
    // Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    
    // Atlas and JSON files for the Animations
    public static final String BREWMASTER_SKELETON_ATLAS = "theBrewmasterResources/images/char/brewmasterAnimation/brewmasterAnimation.atlas";
    public static final String BREWMASTER_SKELETON_JSON = "theBrewmasterResources/images/char/brewmasterAnimation/brewmasterAnimation.json";
    
    // =============== MAKE IMAGE PATHS =================
    
    public static String makeDefaultCardPath(String resourcePath, CardType type) {
        return getModID() + "Resources/images/cards/" + type.toString().toLowerCase() + "/default.png";
    }

    public static String makeCardPath(String resourcePath, CardType type) {
        return getModID() + "Resources/images/cards/" + type.toString().toLowerCase() + "/" + resourcePath + ".png";
    }
    
    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    
    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/images/orbs/" + resourcePath;
    }
    
    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }
    
    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }

    public static String makeUIPath(String resourcePath) {
        return getModID() + "Resources/images/ui/" + resourcePath;
    }

    public static String makeVFXPath(String resourcePath) {
        return getModID() + "Resources/images/vfx/" + resourcePath;
    }

    // =============== /SUBSCRIBER VARIABLES/ =================

    public static int brewCardsPlayedThisCombat;
    public static int brewCardsPlayedThisTurn;
    
    // =============== /INPUT TEXTURE LOCATION/ =================
    
    public BrewmasterMod() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
        BaseMod.subscribe(new BrewmasterSettingsMenu());
      
        setModID("theBrewmaster");
        
        logger.info("Done subscribing");
        
        logger.info("Creating the color " + BrewmasterCharacter.Enums.ORANGE.toString());
        
        BaseMod.addColor(BrewmasterCharacter.Enums.ORANGE, BREWMASTER_ORANGE, BREWMASTER_ORANGE, BREWMASTER_ORANGE,
                BREWMASTER_ORANGE, BREWMASTER_ORANGE, BREWMASTER_ORANGE, BREWMASTER_ORANGE,
                ATTACK_BREWMASTER_ORANGE, SKILL_BREWMASTER_ORANGE, POWER_BREWMASTER_ORANGE, ENERGY_ORB_BREWMASTER_ORANGE,
                ATTACK_BREWMASTER_ORANGE_PORTRAIT, SKILL_BREWMASTER_ORANGE_PORTRAIT, POWER_BREWMASTER_ORANGE_PORTRAIT,
                ENERGY_ORB_BREWMASTER_ORANGE_PORTRAIT, CARD_ENERGY_ORB);
        
        logger.info("Done creating the color");
        
        // logger.info("Adding mod settings");
        // // This loads the mod settings.
        // // The actual mod Button is added below in receivePostInitialize()
        // theBrewmasterDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        // try {
        //     SpireConfig config = new SpireConfig("theBrewmaster", "theBrewmasterConfig", theBrewmasterDefaultSettings); // ...right here
        //     // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
        //     config.load(); // Load the setting and set the boolean to equal it
        //     enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        // logger.info("Done adding mod settings");
        
    }
    // ========= Save and Load =========
    public static void save(){
        try {
            SpireConfig config = new SpireConfig(modID, "settings");
            config.setInt("beerSteinAmount", beerSteinAmount);
            config.setInt("intoxDecayAmount", intoxDecayAmount);
            config.setInt("intoxThreshAmount", intoxThreshAmount);
            config.save();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public static void load(){
        try {
            SpireConfig config = new SpireConfig(modID, "settings");
            config.load();
            if (config.has("beerSteinAmount")){
                beerSteinAmount = config.getInt("beerSteinAmount");
            }
            if (config.has("intoxDecayAmount")){
                intoxDecayAmount = config.getInt("intoxDecayAmount");
            }
            if (config.has("intoxThreshAmount")){
                intoxThreshAmount = config.getInt("intoxThreshAmount");
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

    }
    // ====== Mod Standardization ======
    
    public static void setModID(String ID) { 
        Gson coolG = new Gson();
        InputStream in = BrewmasterMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class);
        logger.info("You are attempting to set your mod ID as: " + ID);
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) {
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION);
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) {
            modID = EXCEPTION_STRINGS.DEFAULTID;
        } else {
            modID = ID;
        } 
        logger.info("Success! ID is " + modID);
    } 
    
    public static String getModID() {
        return modID;
    }
    
    private static void pathCheck() {
        Gson coolG = new Gson();
        InputStream in = BrewmasterMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class);
        String packageName = BrewmasterMod.class.getPackage().getName();
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources");
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) { 
            if (!packageName.equals(getModID())) {
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID());
            }
            if (!resourcePathExists.exists()) { 
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources");
            }
        }
    }    
    
    public static void initialize() {
        logger.info("========================= Initializing BrewmasterMod. Hi. =========================");
        BrewmasterMod brewmastermod = new BrewmasterMod();
        logger.info("========================= /BrewmasterMod Initialized./ =========================");
    }
  
    // =============== LOAD THE CHARACTER =================
    
    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + BrewmasterCharacter.Enums.BREWMASTER.toString());
        
        BaseMod.addCharacter(new BrewmasterCharacter("the Brewmaster", BrewmasterCharacter.Enums.BREWMASTER),
                BREWMASTER_BUTTON, BREWMASTER_PORTRAIT, BrewmasterCharacter.Enums.BREWMASTER);
        
        receiveEditPotions();
        logger.info("Added " + BrewmasterCharacter.Enums.BREWMASTER.toString());
    }
  
    // ================ ADD POTIONS ===================
    
    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");
        
        // Class Specific Potions
        BaseMod.addPotion(FermentedTea.class, FermentedTea.LIQUID_COLOR, FermentedTea.HYBRID_COLOR, FermentedTea.SPOTS_COLOR, FermentedTea.POTION_ID, BrewmasterCharacter.Enums.BREWMASTER);
        BaseMod.addPotion(InertIncendiary.class, InertIncendiary.LIQUID_COLOR, InertIncendiary.HYBRID_COLOR, InertIncendiary.SPOTS_COLOR, InertIncendiary.POTION_ID, BrewmasterCharacter.Enums.BREWMASTER);
        BaseMod.addPotion(ShiftingAle.class, ShiftingAle.LIQUID_COLOR, ShiftingAle.HYBRID_COLOR, ShiftingAle.SPOTS_COLOR, ShiftingAle.POTION_ID, BrewmasterCharacter.Enums.BREWMASTER);
        
        logger.info("Done editing potions");
    }
    
    // ================ ADD RELICS ===================
    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        // Character specific relics.
        BaseMod.addRelicToCustomPool(new BeerSteinRelic(), BrewmasterCharacter.Enums.ORANGE);
        BaseMod.addRelicToCustomPool(new GiantKegRelic(), BrewmasterCharacter.Enums.ORANGE);
        BaseMod.addRelicToCustomPool(new SpiritHelmetRelic(), BrewmasterCharacter.Enums.ORANGE);
        BaseMod.addRelicToCustomPool(new LouseLiverRelic(), BrewmasterCharacter.Enums.ORANGE);
        BaseMod.addRelicToCustomPool(new CozyBeerRelic(), BrewmasterCharacter.Enums.ORANGE);
        BaseMod.addRelicToCustomPool(new EthanolFlaskRelic(), BrewmasterCharacter.Enums.ORANGE);
        BaseMod.addRelicToCustomPool(new OctarineCupRelic(), BrewmasterCharacter.Enums.ORANGE);
        BaseMod.addRelicToCustomPool(new MiniFridgeRelic(), BrewmasterCharacter.Enums.ORANGE);
        BaseMod.addRelicToCustomPool(new SickBagRelic(), BrewmasterCharacter.Enums.ORANGE);

        
        // Mark starter relic as seen
        UnlockTracker.markRelicAsSeen(BeerSteinRelic.ID);
        logger.info("Done adding relics!");
    }
    
    
    // ================ ADD CARDS ===================
    
    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        pathCheck();

        // Add the Custom Dynamic variables
        logger.info("Add variables");

        BaseMod.addDynamicVariable(new DefaultCustomVariable());
        BaseMod.addDynamicVariable(new SecondMagicNumber());
        
        logger.info("Adding cards");

        new AutoAdd("theBrewmaster")
            .packageFilter(AbstractBrewmasterCard.class) // filters to any class in the same package as AbstractDefaultCard, nested packages included
            .setDefaultSeen(true)
            .cards();

        // .setDefaultSeen(true) unlocks the cards
        // This is so that they are all "seen" in the library,
        // for people who like to look at the card list before playing your mod

        // Adding Custom Checks
        logger.info("Adding CustomChecks");

        // theBrewmaster:intoxicated
        // returns 1 if in Intoxicated Stance
        // returns 0 if not
        // returns -1 if not in hand or limbo.
        DynamicTextBlocks.registerCustomCheck(makeID("intoxicated"), card -> {
            if (CardCrawlGame.dungeon != null && AbstractDungeon.player != null){
                if (AbstractDungeon.player.hand.contains(card) || AbstractDungeon.player.limbo.contains(card)){
                    if (AbstractDungeon.player.stance.ID.equals(IntoxicatedStance.STANCE_ID))
                        return 1;
                    return 0;
                }
            }
            return -1;
        });

        // theBrewmaster:perplexing
        // returns -1 if not in hand or limbo
        // returns MagicNumber otherwise
        DynamicTextBlocks.registerCustomCheck(makeID("perplexing"), card ->{
            if (CardCrawlGame.dungeon != null && AbstractDungeon.player != null){
                if (AbstractDungeon.player.hand.contains(card) || AbstractDungeon.player.limbo.contains(card)){
                    return card.magicNumber;
                }
            }
            return -1;
        });

        logger.info("Done adding cards!");
    }    
    
    // ================ LOAD THE TEXT ===================
    
    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings for mod with ID: " + getModID());
        
        // CardStrings
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/BrewmasterMod-Card-Strings.json");
        
        // PowerStrings
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/BrewmasterMod-Power-Strings.json");
        
        // RelicStrings
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/BrewmasterMod-Relic-Strings.json");
        
        // Event Strings
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/BrewmasterMod-Event-Strings.json");
        
        // PotionStrings
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/BrewmasterMod-Potion-Strings.json");
        
        // CharacterStrings
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/BrewmasterMod-Character-Strings.json");
        
        // OrbStrings
        // BaseMod.loadCustomStringsFile(OrbStrings.class,
        //         getModID() + "Resources/localization/eng/BrewmasterMod-Orb-Strings.json");

        // StanceStrings
        BaseMod.loadCustomStringsFile(StanceStrings.class,
                getModID() + "Resources/localization/eng/BrewmasterMod-Stance-Strings.json");

        // UIStrings
        BaseMod.loadCustomStringsFile(UIStrings.class,
                getModID() + "Resources/localization/eng/BrewmasterMod-UI-Strings.json");
        
        logger.info("Done edittting strings");
    }
    
    // ================ LOAD THE KEYWORDS ===================

    @Override
    public void receiveEditKeywords() {
        // Keywords on cards are supposed to be Capitalized, while in Keyword-String.json they're lowercase
        //
        // Multiword keywords on cards are done With_Underscores
        //
        // If you're using multiword keywords, the first element in your NAMES array in your keywords-strings.json has to be the same as the PROPER_NAME.
        // That is, in Card-Strings.json you would have #yA_Long_Keyword (#y highlights the keyword in yellow).
        // In Keyword-Strings.json you would have PROPER_NAME as A Long Keyword and the first element in NAMES be a long keyword, and the second element be a_long_keyword
        
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/BrewmasterMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }

    
    // this adds "ModName:" before the ID of any card/relic/power etc.
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }

    // Implement Subscribers

    @Override
    public void receiveCardUsed(AbstractCard card) {
        if (card.hasTag(CustomTags.BREW)){
            BrewmasterMod.brewCardsPlayedThisCombat++;
            BrewmasterMod.brewCardsPlayedThisTurn++;
        }
        
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom room) {
        AbstractDungeon.player.masterDeck.group.stream().filter(c -> c instanceof Homebrew).forEach(c -> ((Homebrew) c).onBattleStart(room));
        BrewmasterMod.brewCardsPlayedThisCombat = 0;
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        BrewmasterMod.brewCardsPlayedThisTurn = 0;
    }

    public static CardGroup getMicrobrews(){
        CardGroup retVal = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

        retVal.addToTop(new DefendMicro());
        retVal.addToTop(new CinderMicro());
        retVal.addToTop(new VolatileMicro());
        retVal.addToTop(new IntoxMicro());
        // retVal.addToTop(new MedicalMicro());

        return retVal;
    }
}
