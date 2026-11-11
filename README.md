# RecipeApp

An Android recipe app built using Java, XML layouts and Fragments.

## Features

- Reads five recipes from assets/recipes.json.
- Displays recipe names in a ListView.
- Shows the selected recipe's ingredients and method.
- Uses separate Activities for the list and details in portrait.
- Displays both Fragments together in landscape.
- Preserves the selected recipe when rotating the device.
- Supports Back navigation from portrait details to the list.

## Running the app

1. Open the project in Android Studio.
2. Allow Gradle to sync.
3. Connect an Android phone or start an emulator.
4. Select the app configuration and click Run.

## Manual testing

- Confirm all five recipe names appear.
- Open each recipe and check its ingredients and method.
- Check Back returns to the recipe list in portrait.
- Rotate from portrait details to landscape.
- Select another recipe in landscape.
- Rotate back to portrait and check the selected recipe remains.
- Check longer recipe details can be scrolled.

## Navigation note

The brief requests separate Activities in portrait and also provides
a single-Activity Fragment back-stack example. This implementation
follows the separate-Activities requirement, using the Activity back
stack for portrait navigation and FragmentManager for displaying
the Fragments.