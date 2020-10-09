import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

//https://kotlinlang.org/docs/reference/coroutines/basics.html
class Coroutines {
	companion object {

		suspend fun myCoroutineFun() {
			println("hello")
		}

		@JvmStatic
		fun main(args: Array<String>) {

			//1 Basics
			GlobalScope.launch { /* coroutine here */ }
			runBlocking { /* coroutine here */ }
			val a = GlobalScope.async {}
			/* coroutine here */// -- fail


			//2 Dispatchers
			runBlocking {
				launch(Dispatchers.IO) { }

				launch(Dispatchers.Default) { }
				launch(Dispatchers.IO) {}
				launch(Dispatchers.Unconfined) {
				}
				//launch(Dispatchers.Main) { /* coroutine here */ }// -- only Android
				launch(Dispatchers.IO + CoroutineName("my name")) {}

			}

			//3 Context switching
			val c = newSingleThreadContext("single")
			val cc = newSingleThreadContext("thread name")

			GlobalScope.launch {
			}
			runBlocking(c) { }

			runBlocking(c) { /* coroutine here */ }
			runBlocking { withContext(c) {} }
			//withContext(c) { /* coroutine here */ } -- fail

			//4 Scope
			//val scope = MainScope() -- only Android
			val scope = CoroutineScope(Dispatchers.IO)
			scope.launch { /* coroutine here */ }
			scope.cancel()

			val myScope = CoroutineScope(c)
			myScope.cancel()

			val x = CoroutineScope(Dispatchers.IO).launch {
				repeat(100) {
					println(Thread.currentThread().name)
					delay(1)
				}
			}
			runBlocking {
				x.join()
			}
		}

	}
}