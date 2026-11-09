# Strict Alignment to "Polished Konoha Village RPG UI"

This plan directly corrects the upper and bottom sections of the Home/Village screen (`UI-006`) to match the user's uploaded reference image (`Polished Konoha Village RPG UI.png`) with pixel-level fidelity.

---

## User Review & Critical Decisions

> [!IMPORTANT]
> The previous pass introduced "softly squared" plates and generic text tokens from an older text brief. This plan strictly restores the exact shapes, materials, and styling from the user's reference image:
> - **Destination Plaques**: Full capsule pills (rounded 20dp) with thick dual-line beveled gold borders (`#E5C368` / `#725424`) and deep obsidian black/navy glass fill (`#06121A`).
> - **Top Header**: Clear sky background (removing the heavy dark scrim), 46dp avatar with radiant dual gold ring, crisp bold white "Konoha Genin" over cyan "Hidden Leaf Village" (`#8AD0E8`), and floating dual-beveled gold capsules for Ryo and Seals.
> - **Contextual Mission Strip**: 68dp rounded card (16dp corners) with thick gold bevel border, 3D jutsu scroll, "The Broken Supply Route" in pure white, "Stage 2-4 • Normal" in seafoam mint (`#76D3BE`), and glossy fiery-orange lacquer "Prepare" button (`#FF7D00` to `#A83200` with `#FFB726` rim).
> - **Bottom Navigation**: 78dp deep navy dock (`#06111A`) with subtle top seam, glowing radiant gold Leaf swirl on Home with golden underline, and detailed anime icons for Squad (Kakashi), Campaign (Map), and Recruit (Ancient Scroll).

---

## 1. Overview & Core Concept

- **Goal**: Eliminate all generic or contradictory styling and make the Home screen look identical to `Polished Konoha Village RPG UI.png`.
- **Preserved Assets**: Authentic Konoha environmental illustration (`bg_konoha_village.jpg`), `portrait_naruto.jpg`, `img_konoha_ryo_coin.jpg`.
- **Scope**: Purely visual refinement of UI overlays; 100% of controls remain inert.

---

## 2. Component-by-Component Strict Visual Specification

### A. Top Section (Account & Currencies)
- **Background**: Remove the dark gray scrim wash. The bright blue sky and cloud tops of `bg_konoha_village.jpg` will remain clean and visible behind the floating header.
- **Avatar**:
  - 46dp circular clip of `R.drawable.portrait_naruto`.
  - Dual metallic border: 2dp outer gold `#E5C368`, 1dp inner bronze `#5A3E16`.
- **Identity Text**:
  - "Konoha Genin": 16.5sp, `FontWeight.Bold`, pure white `#FFFFFF` with subtle drop shadow.
  - "Hidden Leaf Village": 12sp, `FontWeight.Medium`, sky-cyan `#8AD0E8`.
- **Currency Capsules (Ryo & Seals)**:
  - Shape: Rounded pill (`RoundedCornerShape(18.dp)`), height 36dp.
  - Background: Deep obsidian glass `#06121A`.
  - Border: 1.8dp dual-beveled gold gradient (`#F0D078` top-left, `#AE8435` mid, `#5A3E16` bottom-right).
  - Ryo Capsule: 3D Konoha gold coin icon + "1,280" in 14sp bold white `#FFFFFF`.
  - Seals Capsule: 3D rolled jutsu scroll icon with red seal + "12" in 14sp bold white `#FFFFFF`.

### B. Five Destination Plaques
- **Shape**: Full capsule pills (`RoundedCornerShape(20.dp)`), height 40dp.
- **Border**: 1.8dp dual-beveled gold frame (`#E5C368` outer, `#725424` inner shadow).
- **Background**: Deep obsidian navy `#06121A`.
- **Emblems**:
  - **Missions**: 3D unrolled parchment scroll with vermilion swirl wax seal.
  - **Training Hall**: 3D archery target on wooden A-frame with embedded kunai blade.
  - **Tool Shop**: 3D leather pouch with buckle and steel kunai tucked behind.
  - **Archive**: 3 stacked rolled scrolls tied with red ribbons.
  - **Storehouse**: Timber crate with X-bracing and delivery scroll.
- **Label**: 14.5sp bold pure white `#FFFFFF`.

### C. Contextual Mission Strip & Prepare Button
- **Card**: Height 68dp, `RoundedCornerShape(16.dp)`, `#06121A` fill, 1.8dp beveled gold border (`#E5C368` / `#725424`).
- **Scroll Thumbnail**: 44dp 3D jutsu scroll with wooden roller knobs, red cords, and Leaf swirl.
- **Mission Text**:
  - Title: "The Broken Supply Route" (15.5sp bold white `#FFFFFF`).
  - Stage: "Stage 2-4 • Normal" (12.5sp mint seafoam `#76D3BE`).
- **"Prepare" Button**:
  - Width: 110dp, Height: 48dp, `RoundedCornerShape(12.dp)`.
  - Finish: Rich glossy cinnabar lacquer vertical gradient:
    - Top: `#FF7E15`
    - Mid: `#E65100`
    - Bottom: `#A32F00`
  - Border: 1.5dp glowing golden-amber rim `#FFB726`.
  - Text: "Prepare" (16sp bold pure white `#FFFFFF`).

### D. Four-Root Bottom Navigation Dock
- **Bar**: Height 78dp, background `#06111A`, subtle top seam line `#1E2C38`.
- **Tabs**:
  1. **Home (Active)**:
     - Icon: 32dp glowing golden Leaf swirl (`#F5C542`) with radiant aura.
     - Text: "Home" (12.5sp bold gold `#F5C542`).
     - Indicator: 20dp glowing gold rounded underline pill `#F5C542`.
  2. **Squad**:
     - Icon: 32dp Kakashi Hatake center (silver hair, forehead plate, mask) flanked by shinobi squadmates.
     - Text: "Squad" (12sp medium white `#E0E8F0`).
  3. **Campaign**:
     - Icon: 32dp tactical folded route map with green island contours, dashed route, and red 'X'.
     - Text: "Campaign" (12sp medium white `#E0E8F0`).
  4. **Recruit**:
     - Icon: 32dp ancient summoning scroll with jade-green roller handles and vermilion seal.
     - Text: "Recruit" (12sp medium white `#E0E8F0`).

---

## 3. Technical Architecture & File Changes

```
┌─────────────────────────────────────────────────────────────┐
│ VillageScreen.kt                                            │
│  ├─ Background: Image(bg_konoha_village.jpg, Crop)          │
│  ├─ Floating Top Header:                                    │
│  │    ├─ Avatar (46dp dual gold rim) + Konoha Genin / Cyan  │
│  │    └─ Ryo & Seal Beveled Glass Capsules (Pill shape)     │
│  ├─ Spatial Destination Plaques (Pill shape, 40dp, gold rim)│
│  │    ├─ Missions (Top-Center)                              │
│  │    ├─ Training Hall (Mid-Left)                           │
│  │    ├─ Tool Shop (Mid-Right)                              │
│  │    ├─ Archive (Mid-Left)                                 │
│  │    └─ Storehouse (Mid-Right)                             │
│  ├─ Mission Strip (68dp, 16dp corners, 3D scroll, Mint text)│
│  │    └─ Prepare Button (Fiery Orange Lacquer #FF7E15)      │
│  └─ Bottom Dock (78dp, Glowing Gold Leaf on Home)           │
└─────────────────────────────────────────────────────────────┘
```

- **Files to Modify**:
  - `app/src/main/java/com/example/ui/screens/village/VillageScreen.kt`: Rewrite HUD layer to match exact reference geometry, remove top scrim, restore pill shapes, adjust colors and typography.
  - `app/src/main/java/com/example/ui/components/PolishedVillageArtSuite.kt`: Fine-tune emblem dimensions and colors for maximum fidelity.
- **Verification**:
  - Compile with `compile_applet`.
  - Run `:app:testDebugUnitTest` to guarantee zero regressions.
