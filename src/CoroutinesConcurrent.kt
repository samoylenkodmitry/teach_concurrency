import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicInteger

//https://kotlinlang.org/docs/reference/coroutines/shared-mutable-state-and-concurrency.html
class CoroutinesConcurrent {
	companion object {
		@JvmStatic
		var counter = AtomicInteger(0)

		@JvmStatic
		fun main(args: Array<String>) = runBlocking {
			val coroutinesCount = 10_000
			val incrementsCount = 10_000

			val list = mutableListOf<Job>()
			repeat(coroutinesCount) {
				val x = launch(Dispatchers.IO) {
					repeat(incrementsCount) {
						synchronized(counter) {
							counter.incrementAndGet()
						}
					}
				}
				list += x
			}
			for (job in list) {
				job.join()
			}

			println(counter)
		}
	}
}