package com.example.data

data class GkCategory(
    val id: String,
    val title: String,
    val emoji: String,
    val description: String,
    val items: List<GkItem>
)

data class GkItem(
    val name: String,
    val emoji: String,
    val subtitle: String,
    val detail1: String, // e.g. Where it lives / Taste / Sides
    val detail2: String, // e.g. What it eats / Healthy / Example
    val funFact: String,
    val pronunciation: String
)

data class GkScienceQuestion(
    val question: String,
    val answer: String,
    val options: List<String>,
    val explanation: String,
    val emoji: String
)

object GeneralKnowledgeRepository {

    val categories = listOf(
        GkCategory(
            id = "animals",
            title = "Animals",
            emoji = "🦁",
            description = "Learn about 10 friendly wild and pet animals!",
            items = listOf(
                GkItem(
                    name = "Lion",
                    emoji = "🦁",
                    subtitle = "King of the Jungle",
                    detail1 = "Lives in: Grassy African savannas",
                    detail2 = "Eats: Fresh meat",
                    funFact = "A lion's mighty roar can be heard five miles away!",
                    pronunciation = "Lion. The King of the jungle."
                ),
                GkItem(
                    name = "Tiger",
                    emoji = "🐯",
                    subtitle = "Striped Wild Cat",
                    detail1 = "Lives in: Dense green forests and jungles",
                    detail2 = "Eats: Meat and fish",
                    funFact = "Tigers love swimming in rivers to cool down!",
                    pronunciation = "Tiger. A brave wild cat with black stripes."
                ),
                GkItem(
                    name = "Elephant",
                    emoji = "🐘",
                    subtitle = "Gentle Giant",
                    detail1 = "Lives in: Forests and open grasslands",
                    detail2 = "Eats: Leaves, sweet fruits, and grasses",
                    funFact = "An elephant uses its long trunk to smell and drink!",
                    pronunciation = "Elephant. The largest animal on land."
                ),
                GkItem(
                    name = "Giraffe",
                    emoji = "🦒",
                    subtitle = "Tallest Land Animal",
                    detail1 = "Lives in: African savannas and woodlands",
                    detail2 = "Eats: Acacia leaves from tall tree branches",
                    funFact = "A giraffe's purple tongue can be 18 inches long!",
                    pronunciation = "Giraffe. The tallest animal in the world."
                ),
                GkItem(
                    name = "Zebra",
                    emoji = "🦓",
                    subtitle = "Striped Safari Horse",
                    detail1 = "Lives in: African grasslands",
                    detail2 = "Eats: Fresh green grass and herbs",
                    funFact = "Every single zebra has a unique stripe pattern!",
                    pronunciation = "Zebra. A wild animal with black and white stripes."
                ),
                GkItem(
                    name = "Monkey",
                    emoji = "🐒",
                    subtitle = "Playful Tree Climber",
                    detail1 = "Lives in: High treetop canopies",
                    detail2 = "Eats: Bananas, nuts, and sweet fruits",
                    funFact = "Monkeys use their tails like an extra hand to swing!",
                    pronunciation = "Monkey. Loves swinging between tree branches."
                ),
                GkItem(
                    name = "Rabbit",
                    emoji = "🐰",
                    subtitle = "Fluffy Hopping Bunny",
                    detail1 = "Lives in: Cozy underground burrows",
                    detail2 = "Eats: Carrots, fresh hay, and crunchy greens",
                    funFact = "Rabbits twitch their cute noses to smell everything!",
                    pronunciation = "Rabbit. A soft fluffy animal that hops."
                ),
                GkItem(
                    name = "Dog",
                    emoji = "🐶",
                    subtitle = "Man's Loyal Friend",
                    detail1 = "Lives in: Homes with loving families",
                    detail2 = "Eats: Dog food, meat, and healthy treats",
                    funFact = "Dogs wag their tails to show happiness and excitement!",
                    pronunciation = "Dog. Our loyal best friend that barks woof woof."
                ),
                GkItem(
                    name = "Cat",
                    emoji = "🐱",
                    subtitle = "Cuddly Purring Pet",
                    detail1 = "Lives in: Houses and warm sunny spots",
                    detail2 = "Eats: Fish, meat, and cat treats",
                    funFact = "Cats sleep for around 14 hours every single day!",
                    pronunciation = "Cat. A cute furry pet that purrs softly."
                ),
                GkItem(
                    name = "Cow",
                    emoji = "🐮",
                    subtitle = "Gentle Farm Animal",
                    detail1 = "Lives in: Farm barns and green pastures",
                    detail2 = "Eats: Fresh green grass and hay",
                    funFact = "Cows give us healthy milk to make butter and cheese!",
                    pronunciation = "Cow. A friendly farm animal that gives us milk."
                )
            )
        ),
        GkCategory(
            id = "fruits",
            title = "Fruits",
            emoji = "🍎",
            description = "Explore delicious and healthy fruits!",
            items = listOf(
                GkItem("Apple", "🍎", "Sweet & Crisp", "Color: Red, green, or yellow", "Taste: Sweet and crunchy", "Packed with fiber and Vitamin C!", "Apple. A delicious and crisp fruit."),
                GkItem("Mango", "🥭", "King of Fruits", "Color: Golden yellow", "Taste: Super sweet and juicy", "Grows on big leafy summer trees!", "Mango. The king of all fruits."),
                GkItem("Banana", "🍌", "Energy Booster", "Color: Bright sunny yellow", "Taste: Soft and creamy sweet", "Full of potassium for strong muscles!", "Banana. A peelable yellow fruit full of energy."),
                GkItem("Orange", "🍊", "Juicy Citrus", "Color: Vibrant orange", "Taste: Tangy, sweet, and juicy", "Helps keep your immune system strong!", "Orange. Full of sweet juice and vitamin C."),
                GkItem("Grapes", "🍇", "Vine Bunches", "Color: Purple, green, or black", "Taste: Sweet little bite-sized pearls", "Dried grapes turn into delicious raisins!", "Grapes. Grows in sweet clusters on leafy vines."),
                GkItem("Watermelon", "🍉", "Summer Refreshment", "Color: Green outside, red inside", "Taste: Cool, juicy, and sweet", "Watermelons are over 90 percent water!", "Watermelon. A giant juicy summer fruit."),
                GkItem("Pineapple", "🍍", "Tropical Treat", "Color: Golden with green spiky crown", "Taste: Tangy sweet tropical punch", "Takes almost three years to grow one fruit!", "Pineapple. A sweet tropical fruit with a spiky crown."),
                GkItem("Strawberry", "🍓", "Heart-Shaped Berry", "Color: Shiny bright red", "Taste: Fragrant, sweet, and berry fresh", "The only fruit with seeds on the outside!", "Strawberry. A bright red heart-shaped sweet berry.")
            )
        ),
        GkCategory(
            id = "colors",
            title = "Colors",
            emoji = "🎨",
            description = "Discover the colorful world around us!",
            items = listOf(
                GkItem("Red", "🔴", "Bold and Energetic", "Found in: Ripe apples, strawberries, roses", "Mood: Warm and lively", "Red is the top color of the rainbow!", "Red. A warm and energetic color."),
                GkItem("Blue", "🔵", "Calm and Peaceful", "Found in: Ocean waters, clear sky, blueberries", "Mood: Relaxing and cool", "Blue is the most popular favorite color!", "Blue. The color of the open ocean and bright sky."),
                GkItem("Green", "🟢", "Fresh and Natural", "Found in: Grass, tree leaves, frogs, limes", "Mood: Healthy and fresh", "Green is the color of mother nature!", "Green. The color of healthy plants and leafy trees."),
                GkItem("Yellow", "🟡", "Bright and Cheerful", "Found in: The glowing sun, ripe bananas, lemons", "Mood: Happy and sunny", "Yellow is the brightest color of daytime!", "Yellow. A cheerful color like the warm sunshine."),
                GkItem("Orange", "🟠", "Vibrant and Playful", "Found in: Orange fruit, carrots, autumn leaves", "Mood: Fun and energetic", "Orange is made by mixing red and yellow!", "Orange. A vibrant mix of red and yellow."),
                GkItem("Pink", "🌸", "Sweet and Gentle", "Found in: Cherry blossoms, cotton candy, flamingos", "Mood: Gentle and caring", "Pink is a lighter shade of red mixed with white!", "Pink. A soft sweet and gentle color."),
                GkItem("Purple", "🟣", "Royal and Magic", "Found in: Grapes, lavender flowers, royal velvet", "Mood: Royal and imaginative", "Kings and queens loved wearing purple robes!", "Purple. A magical and royal color."),
                GkItem("Black", "⚫", "Dark and Strong", "Found in: Night sky, tire wheels, coal", "Mood: Deep and quiet", "Black absorbs all colors of light!", "Black. The color of the deep nighttime sky."),
                GkItem("White", "⚪", "Pure and Bright", "Found in: Winter snow, milk, fluffy clouds", "Mood: Clean and peaceful", "White reflects all colors together!", "White. The pure clean color of winter snow."),
                GkItem("Brown", "🟤", "Earthy and Grounded", "Found in: Tree bark, chocolate, teddy bears", "Mood: Warm and cozy", "Brown is the color of rich garden soil!", "Brown. The cozy color of chocolate and tree trunks.")
            )
        ),
        GkCategory(
            id = "body_parts",
            title = "Body Parts",
            emoji = "👀",
            description = "Learn about our amazing human body!",
            items = listOf(
                GkItem("Eyes", "👀", "Sight", "Count: Two eyes", "Function: To see colors, shapes, and smiles", "Blinking keeps our eyes clean and moist!", "Eyes. We use our eyes to see the world."),
                GkItem("Ears", "👂", "Hearing", "Count: Two ears", "Function: To listen to music, voices, and birds", "Ears help us keep our balance too!", "Ears. We use our ears to listen and hear sounds."),
                GkItem("Nose", "👃", "Smell & Breathing", "Count: One nose with two nostrils", "Function: To smell flowers and breathe fresh air", "Your nose can remember thousands of scents!", "Nose. We use our nose to smell and breathe."),
                GkItem("Mouth", "👄", "Taste & Speaking", "Count: One mouth with teeth and tongue", "Function: To talk, smile, sing, and eat", "Your tongue helps you taste sweet and salty!", "Mouth. We use our mouth to talk, smile, and eat."),
                GkItem("Hands", "🖐️", "Touching & Holding", "Count: Two hands with ten fingers", "Function: To draw, clap, hold hands, and build", "Each finger has a special unique fingerprint!", "Hands. We use our hands to write, draw, and hold things."),
                GkItem("Legs", "🦵", "Running & Walking", "Count: Two legs with knees", "Function: To stand tall, jump, dance, and run", "Your leg bones are the strongest in your body!", "Legs. We use our legs to run, walk, and jump."),
                GkItem("Head", "🧒", "Thinking & Protecting", "Count: One head with hair", "Function: Holds our smart brain, eyes, and ears", "Our head holds our wonderful thoughts and ideas!", "Head. Holds our brain to learn and think."),
                GkItem("Fingers", "✌️", "Grasping & Counting", "Count: Five fingers on each hand", "Function: To pick up small toys and count numbers", "Our thumbs allow us to hold spoons and pencils!", "Fingers. We use our fingers to point and count.")
            )
        ),
        GkCategory(
            id = "days_of_week",
            title = "Days of the Week",
            emoji = "📅",
            description = "Learn all 7 days of the week in order!",
            items = listOf(
                GkItem("Monday", "🗓️", "Day 1", "School day", "First day of the learning week", "Start the week with a happy smile!", "Monday. The first day of the school week."),
                GkItem("Tuesday", "🗓️", "Day 2", "School day", "Second day of the week", "Great day for reading exciting books!", "Tuesday. The second day of the week."),
                GkItem("Wednesday", "🗓️", "Day 3", "School day", "Middle of the school week", "Also known as hump day!", "Wednesday. The middle of the week."),
                GkItem("Thursday", "🗓️", "Day 4", "School day", "One day before Friday", "Time for arts, crafts, and games!", "Thursday. The fourth day of the week."),
                GkItem("Friday", "🎉", "Day 5", "School day", "Last school day of the week", "Weekend is about to begin!", "Friday. The final day of the school week."),
                GkItem("Saturday", "🎈", "Day 6", "Weekend", "Fun family day off", "Time for playing outside at the park!", "Saturday. A fun weekend day for family and play."),
                GkItem("Sunday", "☀️", "Day 7", "Weekend", "Relaxing rest day", "Get ready for a wonderful new week!", "Sunday. The peaceful rest day of the weekend.")
            )
        ),
        GkCategory(
            id = "months_of_year",
            title = "Months of the Year",
            emoji = "🗓️",
            description = "Learn all 12 months with pronunciation!",
            items = listOf(
                GkItem("January", "❄️", "Month 1", "Days: 31", "Season: Winter / New Year", "First month of the whole year!", "January. The first month of the year."),
                GkItem("February", "❤️", "Month 2", "Days: 28 or 29", "Season: Winter", "The shortest month of the year!", "February. The second month of the year."),
                GkItem("March", "🌱", "Month 3", "Days: 31", "Season: Spring arrives", "Flowers start blooming everywhere!", "March. Spring begins to arrive."),
                GkItem("April", "🌧️", "Month 4", "Days: 30", "Season: Spring showers", "Brings green grass and colorful blooms!", "April. The fourth month with spring showers."),
                GkItem("May", "🌸", "Month 5", "Days: 31", "Season: Warm spring", "Filled with singing birds and sunshine!", "May. A warm and sunny spring month."),
                GkItem("June", "☀️", "Month 6", "Days: 30", "Season: Summer begins", "School holidays and beach time!", "June. Summer begins with bright sunshine."),
                GkItem("July", "🏖️", "Month 7", "Days: 31", "Season: Warm summer", "Perfect for swimming and ice cream!", "July. The warmest summer month."),
                GkItem("August", "🍉", "Month 8", "Days: 31", "Season: Late summer", "Juicy watermelons and sunny parks!", "August. Enjoying the final summer sunshine."),
                GkItem("September", "🎒", "Month 9", "Days: 30", "Season: Autumn begins", "Back to school with friends!", "September. Autumn arrives and school starts."),
                GkItem("October", "🎃", "Month 10", "Days: 31", "Season: Fall leaves", "Leaves turn orange, red, and gold!", "October. Golden leaves fall from the trees."),
                GkItem("November", "🍂", "Month 11", "Days: 30", "Season: Crisp autumn", "Cozy sweaters and gratitude!", "November. Crisp breezy weather before winter."),
                GkItem("December", "🎄", "Month 12", "Days: 31", "Season: Winter holidays", "Final month with celebrations and snow!", "December. The joyful final month of the year.")
            )
        ),
        GkCategory(
            id = "shapes",
            title = "Shapes",
            emoji = "🔷",
            description = "Learn geometric and everyday shapes!",
            items = listOf(
                GkItem("Circle", "⚪", "Round shape", "Sides: 0 straight sides", "Real life: Coin, clock, dinner plate", "A circle is perfectly curved all around!", "Circle. Round with no straight sides or corners."),
                GkItem("Square", "🟦", "Four equal sides", "Sides: 4 equal sides, 4 corners", "Real life: Building blocks, bread slice, picture frame", "All four sides of a square are exactly equal!", "Square. Four equal sides and four corners."),
                GkItem("Triangle", "🔺", "Three-sided shape", "Sides: 3 straight sides, 3 corners", "Real life: Slice of pizza, roof of a house, traffic cone", "Triangle means three angles!", "Triangle. Has three sides and three sharp corners."),
                GkItem("Rectangle", "🚪", "Four-sided oblong", "Sides: 4 sides (2 long and 2 short)", "Real life: Door, smartphone, book cover", "Opposite sides are parallel and equal!", "Rectangle. Four sides with opposite sides equal."),
                GkItem("Star", "⭐", "Shining shape", "Points: 5 glowing points", "Real life: Starfish, night sky star, holiday topper", "Stars symbolize excellence and success!", "Star. A shining shape with five glowing points."),
                GkItem("Oval", "🥚", "Stretched circle", "Sides: 0 straight sides, curved egg shape", "Real life: Hen egg, rugby ball, mirror", "Like a circle gently squished from two sides!", "Oval. An elongated curved shape like an egg."),
                GkItem("Heart", "❤️", "Loving shape", "Sides: 2 rounded curves meeting at a point", "Real life: Greeting cards, sweet chocolates", "Universal symbol of love and friendship!", "Heart. A sweet shape representing love."),
                GkItem("Diamond", "💎", "Tilted diamond / Rhombus", "Sides: 4 equal tilted sides", "Real life: Flying kite, playing card suit, road sign", "Also called a rhombus in geometry!", "Diamond. A tilted four-sided shape like a flying kite.")
            )
        ),
        GkCategory(
            id = "vehicles",
            title = "Vehicles",
            emoji = "🚗",
            description = "Explore transportation on land, air, and water!",
            items = listOf(
                GkItem("Car", "🚗", "Road transport", "Type: Land vehicle with 4 wheels", "Features: Seat belts, steering wheel, horn", "Takes families safely on daily rides!", "Car. A four-wheeled vehicle driven on roads."),
                GkItem("Bus", "🚌", "Public transit", "Type: Large land vehicle", "Features: Many seats for children and passengers", "School buses are painted bright friendly yellow!", "Bus. A large passenger vehicle for schools and cities."),
                GkItem("Train", "🚆", "Railway vehicle", "Type: Rides on iron railway tracks", "Features: Engine in front, many train cars", "Trains can carry hundreds of passengers together!", "Train. Travels smoothly along railway tracks with a whistle."),
                GkItem("Aeroplane", "✈️", "Air travel", "Type: Flying air vehicle", "Features: Wings, jet engines, pilot cockpit", "Flies high up above fluffy white clouds!", "Aeroplane. Flies high across skies and continents."),
                GkItem("Bicycle", "🚲", "Eco-friendly two-wheeler", "Type: Pedal powered", "Features: Two wheels, handlebars, bell", "Great exercise and fun riding at the park!", "Bicycle. Two wheels powered by your feet."),
                GkItem("Boat", "⛵", "Water transport", "Type: Watercraft", "Features: Hull, sails or motor, life jackets", "Floats gently on lakes, rivers, and oceans!", "Boat. Glides smoothly across water."),
                GkItem("Truck", "🚚", "Heavy cargo carrier", "Type: Large transport truck", "Features: Big powerful wheels and spacious bed", "Delivers toys, food, and goods across cities!", "Truck. A strong vehicle built to carry heavy cargo.")
            )
        ),
        GkCategory(
            id = "nature",
            title = "Nature & Environment",
            emoji = "🌳",
            description = "Learn about the wonderful earth and sky!",
            items = listOf(
                GkItem("Sun", "☀️", "Golden Star", "Location: Center of solar system", "Gift: Warmth, daylight, and life", "Plants need sunshine to make food!", "Sun. The glowing star that gives light and warmth."),
                GkItem("Moon", "🌙", "Night Lantern", "Location: Orbits around our Earth", "Gift: Soft nighttime glow and tides", "The moon changes phases from crescent to full!", "Moon. Shines beautifully in the nighttime sky."),
                GkItem("Stars", "✨", "Twinkling Lights", "Location: Far away in deep space", "Gift: Sparkling beauty in the night sky", "Billions of stars twinkle like diamonds!", "Stars. Millions of sparkling lights in the universe."),
                GkItem("Clouds", "☁️", "Fluffy Skies", "Location: Floating high in the air", "Gift: Made of tiny water droplets", "When clouds get heavy, they bring rain!", "Clouds. Fluffy white and gray shapes in the sky."),
                GkItem("Rain", "🌧️", "Fresh Water Drops", "Location: Falling from clouds", "Gift: Gives drinking water to plants and animals", "After the rain stops, a rainbow can appear!", "Rain. Water drops that nourish trees and flowers."),
                GkItem("Trees", "🌳", "Nature's Lungs", "Location: Parks, forests, gardens", "Gift: Clean oxygen, sweet fruits, cool shade", "Birds build their cozy nests in high branches!", "Trees. Tall plants that give us clean air and shade."),
                GkItem("Flowers", "🌺", "Colorful Blossoms", "Location: Gardens and meadows", "Gift: Fragrance and nectar for bees", "Bees collect nectar to make golden honey!", "Flowers. Colorful and fragrant blossoms in nature."),
                GkItem("Mountains", "🏔️", "Majestic Peaks", "Location: Reaching up toward the sky", "Gift: Fresh rivers and scenic snowy peaks", "Home to eagles, snow leopards, and goats!", "Mountains. Tall rocky peaks that touch the clouds.")
            )
        )
    )

    val scienceQuestions = listOf(
        GkScienceQuestion(
            question = "How many eyes do we have?",
            answer = "Two",
            options = listOf("One", "Two", "Three", "Four"),
            explanation = "We have two eyes to see all the beautiful things around us!",
            emoji = "👀"
        ),
        GkScienceQuestion(
            question = "Which animal gives us milk?",
            answer = "Cow",
            options = listOf("Lion", "Cow", "Tiger", "Dog"),
            explanation = "Cows live on farms and give us healthy nutritious milk!",
            emoji = "🐮"
        ),
        GkScienceQuestion(
            question = "Which planet do we live on?",
            answer = "Earth",
            options = listOf("Mars", "Moon", "Earth", "Sun"),
            explanation = "We live on Planet Earth, a beautiful blue and green home!",
            emoji = "🌍"
        ),
        GkScienceQuestion(
            question = "What do plants need to grow?",
            answer = "Water and sunlight",
            options = listOf("Candy and soda", "Water and sunlight", "Toys and books", "Ice and cold"),
            explanation = "Plants drink water from the soil and bathe in warm sunlight to grow tall!",
            emoji = "🌱"
        ),
        GkScienceQuestion(
            question = "Which star gives us light during the day?",
            answer = "Sun",
            options = listOf("Moon", "Sun", "Mars", "Cloud"),
            explanation = "The Sun is our closest star and brightens up the morning sky!",
            emoji = "☀️"
        ),
        GkScienceQuestion(
            question = "What do we use to see?",
            answer = "Eyes",
            options = listOf("Nose", "Ears", "Eyes", "Hands"),
            explanation = "Our eyes capture light so our brain can see pictures and colors!",
            emoji = "👁️"
        ),
        GkScienceQuestion(
            question = "What color is a ripe banana?",
            answer = "Yellow",
            options = listOf("Blue", "Purple", "Yellow", "Pink"),
            explanation = "Ripe bananas are sweet, delicious, and bright yellow!",
            emoji = "🍌"
        )
    )
}
