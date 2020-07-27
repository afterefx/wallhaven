package app.androiddev.wallhaven.model.wallhavendata

/**
 * Parameter
 * @param  q - Search query - Your main way of finding what you're looking for
 *      tagname - search fuzzily for a tag/keyword
 *      -tagname - exclude a tag/keyword
 *      +tag1 +tag2 - must have tag1 and tag2
 *      +tag1 -tag2 - must have tag1 and NOT tag2
 *      @username - user uploads
 *      id:123 - Exact tag search (can not be combined)
 *      type:{png/jpg} - Search for file type (jpg = jpeg)
 *      like:wallpaper ID - Find wallpapers with similar tags
 * @param categories - Turn categories on(1) or off(0)  (e.g. 100/101/111/etc (general/anime/people))
 * @param purity - Turn purities on(1) or off(0)   (e.g. 100/110/111/etc (sfw/sketchy/nsfw) NSFW requires a valid API key)
 * @param sorting  - Method of sorting results Accepted values: date_added, relevance, random, views, favorites, toplist
 * @param order - Sorting order. Accepted values: desc, asc
 * @param topRange - Sorting MUST be set to 'toplist'. Accepted values: 1d, 3d, 1w, 1M, 3M, 6M, 1y
 * @param atleast -  Minimum resolution allowed (e.g. 1920x1080)
 * @param resolutions - List of exact wallpaper resolutions comma separated. (e.g. 1920x1080, 1920x1200) Only a single resolution is allowed
 * @param ratios - List of aspect ratios comma separated. Single ratio allowed (e.g. 16x9,16x10)
 * @param colors - Search by color. Accepted values: 660000, 990000, cc0000 cc3333 ea4c88 993399 663399 333399 0066cc 0099cc 66cccc 77cc33 669900 336600 666600 999900 cccc33 ffff00 ffcc33 ff9900 ff6600 cc6633 996633 663300 000000 999999 cccccc ffffff 424153
 * @param page - Pagination (e.g. 1 - n)
 * @param seed - Optional seed for random results. Accepted values: [a-zA-Z0-9]{6}
 */
data class SearchQuery(
    val query: String = "",
    val categories: Category = Category.All,
    val purity: Purity = Purity.Sfw,
    val sorting: Sort = Sort.Date_added,
    val order: Order = Order.Desc,
    val topRange: TopRange = TopRange.OneMonth,
    val atleast: String = "",
    val resolutions: String = "",
    val ratios: String = "",
    val colors: Colors = Colors.NONE,
    val page: Int = 1,
    val seed: String = ""
)

fun SearchQuery.query(query: String): SearchQuery = this.copy(query = query)
fun SearchQuery.category(category: Category): SearchQuery = this.copy(categories = category)
fun SearchQuery.purity(purity: Purity): SearchQuery = this.copy(purity = purity)
fun SearchQuery.sort(sort: Sort): SearchQuery = this.copy(sorting = sort)
fun SearchQuery.order(order: Order): SearchQuery = this.copy(order = order)
fun SearchQuery.topRange(topRange: TopRange): SearchQuery = this.copy(topRange = topRange)
fun SearchQuery.minimumResolution(resolution: String): SearchQuery = this.copy(atleast = resolution)
fun SearchQuery.resolutions(resolutions: String): SearchQuery = this.copy(resolutions = resolutions)
fun SearchQuery.ratios(ratios: String): SearchQuery = this.copy(ratios = ratios)
fun SearchQuery.colors(colors: Colors): SearchQuery = this.copy(colors = colors)
fun SearchQuery.page(page: Int): SearchQuery = this.copy(page = page)
fun SearchQuery.seed(seed: String): SearchQuery = this.copy(seed = seed)

sealed class Category(val string: String) {
    object General : Category("100")
    object Anime : Category("010")
    object People : Category("001")
    object GeneralOrAnime : Category("110")
    object GeneralOrPeople : Category("101")
    object AnimeOrPeople : Category("011")
    object All : Category("111")
}

sealed class Purity(val string: String) {
    object Sfw : Purity("100")
    object Sketchy : Purity("010")
    object Nsfw : Purity("001")
    object SfwOrSketchy : Purity("110")
    object SfwOrNsfw : Purity("101")
    object SketchyOrNsfw : Purity("011")
    object All : Purity("111")
}

sealed class Sort(val string: String) {
    object Date_added : Sort("date_added")
    object Relevance : Sort("relevance")
    object Random : Sort("random")
    object Views : Sort("views")
    object Favorites : Sort("favorites")
    object Toplist : Sort("toplist")
}

sealed class Order(val string: String) {
    object Desc : Order("desc")
    object Asc : Order("asc")
}

sealed class TopRange(val string: String) {
    object OneDay : TopRange("1d")
    object ThreeDays : TopRange("3d")
    object OneWeek : TopRange("1w")
    object OneMonth : TopRange("1M")
    object ThreeMonths : TopRange("3M")
    object SixMonths : TopRange("6M")
    object OneYear : TopRange("1y")
}

sealed class Colors(val string: String) {
    object a : Colors("660000")
    object b : Colors("990000")
    object c : Colors("cc0000")
    object d : Colors("cc3333")
    object e : Colors("ea4c88")
    object f : Colors("993399")
    object g : Colors("663399")
    object h : Colors("333399")
    object i : Colors("0066cc")
    object j : Colors("0099cc")
    object k : Colors("66cccc")
    object l : Colors("77cc33")
    object m : Colors("669900")
    object n : Colors("336600")
    object o : Colors("666600")
    object p : Colors("999900")
    object q : Colors("cccc33")
    object r : Colors("ffff00")
    object s : Colors("ffcc33")
    object t : Colors("ff9900")
    object u : Colors("ff6600")
    object v : Colors("cc6633")
    object w : Colors("996633")
    object x : Colors("663300")
    object y : Colors("000000")
    object z : Colors("999999")
    object aa : Colors("cccccc")
    object ab : Colors("ffffff")
    object ac : Colors("424153")
    object NONE : Colors("")
}