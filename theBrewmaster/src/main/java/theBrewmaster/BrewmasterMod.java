package theBrewmaster;

import basemod.*;
import basemod.eventUtil.AddEventParams;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.DynamicTextBlocks;
import theBrewmaster.cards.*;
import theBrewmaster.characters.BrewmasterCharacter;
import theBrewmaster.events.IdentityCrisisEvent;
import theBrewmaster.potions.FermentedTea;
import theBrewmaster.potions.InertIncendiary;
import theBrewmaster.potions.ShiftingAle;
import theBrewmaster.relics.BeerSteinRelic;
import theBrewmaster.relics.EthanolFlaskRelic;
import theBrewmaster.relics.CozyBeerRelic;
import theBrewmaster.relics.GiantKegRelic;
import theBrewmaster.relics.LouseLiverRelic;
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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
@SpireInitializer
public class BrewmasterMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        OnStartBattleSubscriber {
    public static final Logger logger = LogManager.getLogger(BrewmasterMod.class.getName());
    private static String modID;

    // Mod-settings settings. This is if you want an on/off savable button
    public static Properties theBrewmasterDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true; // The boolean we'll be setting on/off (true/false)

    //This is for the in-game mod settings panel.
    private static final String MODNAME = "The Brewmaster";
    private static final String AUTHOR = "Gremious"; // And pretty soon - You!
    private static final String DESCRIPTION = "A base for Slay the Spire to start your own mod from, feat. the Default.";
    
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
    private static final String BREWMASTER_BUTTON = "theBrewmasterResources/images/charSelect/DefaultCharacterButton.png";
    private static final String BREWMASTER_PORTRAIT = "theBrewmasterResources/images/charSelect/DefaultCharacterPortraitBG.png";
    public static final String BREWMASTER_SHOULDER_1 = "theBrewmasterResources/images/char/defaultCharacter/shoulder.png";
    public static final String BREWMASTER_SHOULDER_2 = "theBrewmasterResources/images/char/defaultCharacter/shoulder2.png";
    public static final String BREWMASTER_CORPSE = "theBrewmasterResources/images/char/defaultCharacter/corpse.png";
    
    // Mod Badge - A small icon that appears in the mod settings menu next to your mod.
    public static final String BADGE_IMAGE = "theBrewmasterResources/images/Badge.png";
    
    // Atlas and JSON files for the Animations
    public static final String BREWMASTER_SKELETON_ATLAS = "theBrewmasterResources/images/char/defaultCharacter/skeleton.atlas";
    public static final String BREWMASTER_SKELETON_JSON = "theBrewmasterResources/images/char/defaultCharacter/skeleton.json";
    
    // =============== MAKE IMAGE PATHS =================
    
    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
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
    
    // =============== /INPUT TEXTURE LOCATION/ =================
    
    public BrewmasterMod() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
      
        setModID("theBrewmaster");
        
        logger.info("Done subscribing");
        
        logger.info("Creating the color " + BrewmasterCharacter.Enums.ORANGE.toString());
        
        BaseMod.addColor(BrewmasterCharacter.Enums.ORANGE, BREWMASTER_ORANGE, BREWMASTER_ORANGE, BREWMASTER_ORANGE,
                BREWMASTER_ORANGE, BREWMASTER_ORANGE, BREWMASTER_ORANGE, BREWMASTER_ORANGE,
                ATTACK_BREWMASTER_ORANGE, SKILL_BREWMASTER_ORANGE, POWER_BREWMASTER_ORANGE, ENERGY_ORB_BREWMASTER_ORANGE,
                ATTACK_BREWMASTER_ORANGE_PORTRAIT, SKILL_BREWMASTER_ORANGE_PORTRAIT, POWER_BREWMASTER_ORANGE_PORTRAIT,
                ENERGY_ORB_BREWMASTER_ORANGE_PORTRAIT, CARD_ENERGY_ORB);
        
        logger.info("Done creating the color");
        
        logger.info("Adding mod settings");
        // This loads the mod settings.
        // The actual mod Button is added below in receivePostInitialize()
        theBrewmasterDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE"); // This is the default setting. It's actually set...
        try {
            SpireConfig config = new SpireConfig("theBrewmaster", "theBrewmasterConfig", theBrewmasterDefaultSettings); // ...right here
            // the "fileName" parameter is the name of the file MTS will create where it will save our setting.
            config.load(); // Load the setting and set the boolean to equal it
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
        
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
    
    // =============== POST-INITIALIZE =================
    
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");
        
        // Load the Mod Badge
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();
        
        // Create the on/off button:
        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont, // Position (trial and error it), color, font
                enablePlaceholder, // Boolean it uses
                settingsPanel, // The mod panel in which this button will be in
                (label) -> {}, // thing??????? idk
                (button) -> { // The actual button:
            
            enablePlaceholder = button.enabled; // The boolean true/false will be whether the button is enabled or not
            try {
                // And based on that boolean, set the settings and save them
                SpireConfig config = new SpireConfig("theBrewmaster", "theBrewmasterConfig", theBrewmasterDefaultSettings);
                config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                config.save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        settingsPanel.addUIElement(enableNormalsButton); // Add the button to the settings panel. Button is a go.
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

        
        // =============== EVENTS =================
        // https://github.com/daviscook477/BaseMod/wiki/Custom-Events

        // You can add the event like so:
        // BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);
        // Then, this event will be exclusive to the City (act 2), and will show up for all characters.
        // If you want an event that's present at any part of the game, simply don't include the dungeon ID

        // If you want to have more specific event spawning (e.g. character-specific or so)
        // deffo take a look at that basemod wiki link as well, as it explains things very in-depth
        // btw if you don't provide event type, normal is assumed by default

        // Create a new event builder
        // Since this is a builder these method calls (outside of create()) can be skipped/added as necessary
        AddEventParams eventParams = new AddEventParams.Builder(IdentityCrisisEvent.ID, IdentityCrisisEvent.class) // for this specific event
            .dungeonID(TheCity.ID) // The dungeon (act) this event will appear in
            .playerClass(BrewmasterCharacter.Enums.BREWMASTER) // Character specific event
            .create();

        // Add the event
        BaseMod.addEvent(eventParams);

        // =============== /EVENTS/ =================
        logger.info("Done loading badge Image and mod options");
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
            .packageFilter(AbstractDefaultCard.class) // filters to any class in the same package as AbstractDefaultCard, nested packages included
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

    @Override
    public void receiveOnBattleStart(AbstractRoom arg0) {
        AbstractDungeon.player.potions.stream().filter(p -> p instanceof ShiftingAle).forEach(p -> ((ShiftingAle) p).onBattleStart(arg0));
        
    }
}
