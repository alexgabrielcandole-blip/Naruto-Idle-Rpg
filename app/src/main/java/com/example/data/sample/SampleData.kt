package com.example.data.sample

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.*

enum class ShinobiRole(val label: String, val badgeColor: Color) {
    GUARDIAN("Guardian", LeafGreen),
    FINISHER("Finisher", NarutoOrange),
    FLEXIBLE_STRIKER("Striker", NarutoOrangeDark),
    HEALER("Healer", HealGreen),
    CONTROLLER("Controller", GenjutsuPurple),
    SCOUT("Scout", ChakraCyan),
    AOE_BREAKER("AoE Breaker", VillageVermilion),
    BRUISER("Bruiser", ShinobiSteel)
}

enum class ShinobiAffiliation(val label: String) {
    KONOHA("Leaf Village"),
    SUNA("Sand Village"),
    ANBU("ANBU Black Ops")
}

data class SampleShinobi(
    val id: String,
    val name: String,
    val title: String,
    val role: ShinobiRole,
    val affiliation: ShinobiAffiliation,
    val level: Int = 10,
    val maxLevel: Int = 20,
    val hp: Int,
    val atk: Int,
    val def: Int,
    val speed: Int,
    val chakra: Int = 0,
    val basicSkillName: String,
    val techniqueName: String,
    val ultimateName: String,
    val techniqueDesc: String,
    val ultimateDesc: String,
    val avatarEmoji: String,
    val isStarter: Boolean = false,
    val isOwned: Boolean = true,
    val isDeployed: Boolean = false,
    val formationSlot: Int? = null // 0,1 = Front, 2,3,4 = Rear
)

data class SampleCurrency(
    val ryo: Long = 1280L,
    val recruitmentSeals: Int = 12,
    val universalFragments: Int = 45,
    val shinobiExp: Long = 3400L,
    val commonEssence: Int = 120,
    val enhancementMaterials: Int = 48
)

enum class ItemCategory(val label: String) {
    SUPPLIES("Supplies"),
    EQUIPMENT("Equipment"),
    APPEARANCE("Appearance")
}

data class SampleShopItem(
    val id: String,
    val name: String,
    val category: ItemCategory,
    val description: String,
    val price: Int,
    val currencyName: String,
    val quantity: String,
    val isSoldOut: Boolean = false,
    val isOwned: Boolean = false,
    val iconEmoji: String
)

object SampleRepository {
    val sampleCurrency = SampleCurrency()

    val sampleShinobiList = listOf(
        SampleShinobi(
            id = "naruto_01",
            name = "Naruto Uzumaki",
            title = "Hero of the Leaf",
            role = ShinobiRole.FLEXIBLE_STRIKER,
            affiliation = ShinobiAffiliation.KONOHA,
            level = 10,
            hp = 6000,
            atk = 1000,
            def = 1000,
            speed = 100,
            chakra = 40,
            basicSkillName = "Kunai Strike",
            techniqueName = "Shadow Clone Combo",
            ultimateName = "Giant Rasengan",
            techniqueDesc = "Summons clones to strike frontline enemy for 1.30x ATK damage (+15 Chakra).",
            ultimateDesc = "Charges dense chakra sphere dealing 1.85x massive damage (Cost: 100 Chakra).",
            avatarEmoji = "🍥",
            isStarter = true,
            isOwned = true,
            isDeployed = true,
            formationSlot = 2 // Rear Left
        ),
        SampleShinobi(
            id = "sasuke_01",
            name = "Sasuke Uchiha",
            title = "Avenger of the Clan",
            role = ShinobiRole.FINISHER,
            affiliation = ShinobiAffiliation.KONOHA,
            level = 10,
            hp = 5100,
            atk = 1250,
            def = 850,
            speed = 110,
            chakra = 65,
            basicSkillName = "Chidori Blade",
            techniqueName = "Lion Combo",
            ultimateName = "Kirin",
            techniqueDesc = "Converts Marked or low-HP targets into a knockout strike for 1.40x ATK damage.",
            ultimateDesc = "Draws natural lightning dealing 1.85x execute damage to targets under 30% HP.",
            avatarEmoji = "⚡",
            isStarter = true,
            isOwned = true,
            isDeployed = true,
            formationSlot = 1 // Front Right
        ),
        SampleShinobi(
            id = "sakura_01",
            name = "Sakura Haruno",
            title = "Medical Prodigy",
            role = ShinobiRole.HEALER,
            affiliation = ShinobiAffiliation.KONOHA,
            level = 10,
            hp = 6300,
            atk = 800,
            def = 1000,
            speed = 95,
            chakra = 25,
            basicSkillName = "Chakra Punch",
            techniqueName = "Cellular Cleanse",
            ultimateName = "Cherry Blossom Impact",
            techniqueDesc = "Cleanses 1 Wound debuff from an ally and stabilizes their health window.",
            ultimateDesc = "Restores 1.35x Healing ATK to all wounded allies.",
            avatarEmoji = "🌸",
            isStarter = true,
            isOwned = true,
            isDeployed = true,
            formationSlot = 4 // Rear Right
        ),
        SampleShinobi(
            id = "kakashi_01",
            name = "Kakashi Hatake",
            title = "The Copy Ninja",
            role = ShinobiRole.GUARDIAN,
            affiliation = ShinobiAffiliation.KONOHA,
            level = 10,
            hp = 7800,
            atk = 800,
            def = 1200,
            speed = 90,
            chakra = 50,
            basicSkillName = "Iron Kunai",
            techniqueName = "Earth Style: Mud Wall",
            ultimateName = "Lightning Blade",
            techniqueDesc = "Applies 2-hit Taunt to intercept enemy single-target strikes toward allies.",
            ultimateDesc = "Refreshes Taunt shield and absorbs up to 10% max HP damage.",
            avatarEmoji = "📖",
            isStarter = true,
            isOwned = true,
            isDeployed = true,
            formationSlot = 0 // Front Left
        ),
        SampleShinobi(
            id = "shikamaru_01",
            name = "Shikamaru Nara",
            title = "Shadow Tactician",
            role = ShinobiRole.CONTROLLER,
            affiliation = ShinobiAffiliation.KONOHA,
            level = 10,
            hp = 5700,
            atk = 850,
            def = 950,
            speed = 110,
            chakra = 15,
            basicSkillName = "Shadow Shuriken",
            techniqueName = "Shadow Possession Jutsu",
            ultimateName = "Shadow Strangle Jutsu",
            techniqueDesc = "Interrupts enemy charged jutsu and inflicts Silence for 1 target action slot.",
            ultimateDesc = "Binds all enemy actions and delays opposing initiative queue.",
            avatarEmoji = "♟️",
            isStarter = false,
            isOwned = true,
            isDeployed = true,
            formationSlot = 3 // Rear Middle
        ),
        SampleShinobi(
            id = "hinata_01",
            name = "Hinata Hyuga",
            title = "Byakugan Princess",
            role = ShinobiRole.SCOUT,
            affiliation = ShinobiAffiliation.KONOHA,
            level = 10,
            hp = 5700,
            atk = 850,
            def = 950,
            speed = 115,
            chakra = 0,
            basicSkillName = "Gentle Step",
            techniqueName = "Byakugan Perception",
            ultimateName = "Twin Lion Fists",
            techniqueDesc = "Applies Mark (+10% vulnerability) on vulnerable frontline target.",
            ultimateDesc = "Strikes tenketsu points draining enemy chakra meters.",
            avatarEmoji = "👁️",
            isStarter = true,
            isOwned = true,
            isDeployed = false
        ),
        SampleShinobi(
            id = "rocklee_01",
            name = "Rock Lee",
            title = "Green Beast of the Leaf",
            role = ShinobiRole.BRUISER,
            affiliation = ShinobiAffiliation.KONOHA,
            level = 10,
            hp = 6900,
            atk = 950,
            def = 1100,
            speed = 100,
            chakra = 20,
            basicSkillName = "Leaf Hurricane",
            techniqueName = "Primary Lotus",
            ultimateName = "Fifth Gate Open",
            techniqueDesc = "Deals sustained taijutsu damage while absorbing incoming direct hits.",
            ultimateDesc = "Massive speed and attack surge at the cost of slight self-strain.",
            avatarEmoji = "🥋",
            isStarter = false,
            isOwned = true,
            isDeployed = false
        ),
        SampleShinobi(
            id = "tenten_01",
            name = "Tenten",
            title = "Weapons Specialist",
            role = ShinobiRole.AOE_BREAKER,
            affiliation = ShinobiAffiliation.KONOHA,
            level = 10,
            hp = 6000,
            atk = 950,
            def = 950,
            speed = 100,
            chakra = 30,
            basicSkillName = "Twin Rising Dragons",
            techniqueName = "Scroll Barrage",
            ultimateName = "Bashosen Wind Fan",
            techniqueDesc = "Fires a flurry of weapons hitting all rear-row targets, bypassing decoys.",
            ultimateDesc = "Sweeps the entire opposing battlefield with elemental weapons.",
            avatarEmoji = "🎯",
            isStarter = false,
            isOwned = true,
            isDeployed = false
        )
    )

    val sampleShopItems = listOf(
        SampleShopItem(
            id = "item_01",
            name = "Standard Kunai Set",
            category = ItemCategory.EQUIPMENT,
            description = "Standard-issue balanced throwing knives (+150 ATK). Standard gear baseline.",
            price = 300,
            currencyName = "Ryo",
            quantity = "In Stock",
            iconEmoji = "🗡️"
        ),
        SampleShopItem(
            id = "item_02",
            name = "Reinforced Flak Vest",
            category = ItemCategory.EQUIPMENT,
            description = "Leaf Chunin standard protection vest (+800 HP, +120 DEF).",
            price = 500,
            currencyName = "Ryo",
            quantity = "In Stock",
            iconEmoji = "🦺"
        ),
        SampleShopItem(
            id = "item_03",
            name = "Chakra Pill Ration",
            category = ItemCategory.SUPPLIES,
            description = "Nutritious ninja military pills for training fatigue recovery.",
            price = 100,
            currencyName = "Ryo",
            quantity = "x5",
            iconEmoji = "💊"
        ),
        SampleShopItem(
            id = "item_04",
            name = "Sealing Scroll Pack",
            category = ItemCategory.SUPPLIES,
            description = "Essential parchment scrolls for Academy Training Hall promotion.",
            price = 250,
            currencyName = "Ryo",
            quantity = "x3",
            iconEmoji = "📜"
        ),
        SampleShopItem(
            id = "item_05",
            name = "Recruitment Seal",
            category = ItemCategory.SUPPLIES,
            description = "Formal assignment order seal for summoning Leaf shinobi.",
            price = 500,
            currencyName = "Ryo",
            quantity = "x1",
            iconEmoji = "🧧"
        ),
        SampleShopItem(
            id = "item_06",
            name = "Hokage Cloak Outfit",
            category = ItemCategory.APPEARANCE,
            description = "Ceremonial white and red flame-trimmed cloak. Pure visual cosmetic.",
            price = 1200,
            currencyName = "Ryo",
            quantity = "Exclusive",
            iconEmoji = "👘"
        ),
        SampleShopItem(
            id = "item_07",
            name = "ANBU Operative Mask",
            category = ItemCategory.APPEARANCE,
            description = "Porcelain animal mask worn by covert operations shinobi.",
            price = 800,
            currencyName = "Ryo",
            quantity = "Exclusive",
            iconEmoji = "🎭"
        )
    )
}
