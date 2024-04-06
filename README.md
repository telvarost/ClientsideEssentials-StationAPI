# ClientsideEssentials StationAPI Edition for Minecraft Beta 1.7.3

A b1.7.3 mod with StationAPI that fixes some common graphical issues, adds some missing sounds, adds a basic score mechanic, and adds extra keybindings if MojangFixStationAPI/MojangFix is installed.
All changes are configurable with [GlassConfigAPI](https://modrinth.com/mod/glass-config-api).
- Incompatible with UniTweaks (UniTweaks does the same thing and more than this mod)
- Incompatible with FinalBeta (FinalBeta does the same thing and less than this mod, speaking of clientside only here, checkout GameplayEssentials for what's lacking in this mod)

## Changes to Score

Moved to new Scoring/Achievement mod below:
- https://github.com/telvarost/WhatAreYouScoring-StationAPI

## Graphical Fixes

- Render bow correctly when held by player and skeletons
- Fix items rendering underneath container text
- Fix death screen displaying &e0 instead of coloring score as yellow
- Fix minecart flickering when riding on rails
- Fix leg armor rendering in vehicles
- Fix un-named slabs crashing the game

## Graphical Additions

- (In progress) Ability to change gamma light levels in options
- Ability to change FPS limit in options
- Ability to change FOV in options
  - Added `LCtrl` to zoom in on things quickly
  - Install MojangFixStationAPI/MojangFix to change this keybinding
- Ability to change cloud height in options
- Ability to show/hide clouds in options
- Ability to change fog density in options
- Add day counter in debug screen
  - Number in parentheses is real world days
- Ability to disable id tags in debug screen
  - Tags are disabled by default, re-enable using the mod's config

## Sound Additions

- Add chest open sound
- Add chest close sound
- Add food eat sound
- Add food burp sound
  - Plays when a food heals you above max health
- Add item break sound
- Add sheep shear sound
- Add minecart rolling sound

## Keybinding Additions
- Add keybinding for dismounting a vehicle (default: `LShift`)

All other additional keybindings are only added if MojangFixStationAPI/MojangFix is installed.
- Add keybinding for hiding player HUD (default: `F1`)
- Add keybinding for taking a screenshot (default: `F2`)
- Add keybinding for toggling debug HUD (default: `F3`)
- Add keybinding for togglien third-person mode (default: `F5`)
- Add keybinding for toggling cinematic camera mode (default: `F6`)
- Add keybinding for toggling fullscreen mode (default: `F11`)
- Add keybinding for zooming in/out (default: `LCtrl`)
- Add keybinding for hotbar slot 1 (default: `1`)
- Add keybinding for hotbar slot 2 (default: `2`)
- Add keybinding for hotbar slot 3 (default: `3`)
- Add keybinding for hotbar slot 4 (default: `4`)
- Add keybinding for hotbar slot 5 (default: `5`)
- Add keybinding for hotbar slot 6 (default: `6`)
- Add keybinding for hotbar slot 7 (default: `7`)
- Add keybinding for hotbar slot 8 (default: `8`)
- Add keybinding for hotbar slot 9 (default: `9`)

## Installation using Prism Launcher

1. Download an instance of Babric for Prism Launcher: https://github.com/babric/prism-instance
2. Install Java 17, set the instance to use it, and disable compatibility checks on the instance: https://adoptium.net/temurin/releases/
3. Add StationAPI to the mod folder for the instance: https://modrinth.com/mod/stationapi
4. (Optional) Add Mod Menu to the mod folder for the instance: https://modrinth.com/mod/modmenu-beta
5. (Optional) Add GlassConfigAPI 2.0+ to the mod folder for the instance: https://modrinth.com/mod/glass-config-api
6. Add this mod to the mod folder for the instance: https://github.com/telvarost/ClientsideEssentials-StationAPI/releases
7. Run and enjoy! 👍

## Feedback

Got any suggestions on what should be added next? Feel free to share it by [creating an issue](https://github.com/telvarost/ClientsideEssentials-StationAPI/issues/new). Know how to code and want to do it yourself? Then look below on how to get started.

## Contributing

Thanks for considering contributing! To get started fork this repository, make your changes, and create a PR.

If you are new to StationAPI consider watching the following videos on Babric/StationAPI Minecraft modding: https://www.youtube.com/watch?v=9-sVGjnGJ5s&list=PLa2JWzyvH63wGcj5-i0P12VkJG7PDyo9T
