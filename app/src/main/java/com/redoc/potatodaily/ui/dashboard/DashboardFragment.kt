package com.redoc.potatodaily.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.redoc.potatodaily.databinding.FragmentDashboardBinding
import com.redoc.potatodaily.db.BoardEntity
import com.redoc.potatodaily.ui.dashboard.DashAddActivity.Companion.TAG

class DashboardFragment : Fragment(), RecyclerViewAdapter.RowClickListener {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    // 나중에 값이 설정될거라고 lateinit
    lateinit var viewModel: DashboardViewModel
    lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"resume")

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewAdapter = RecyclerViewAdapter(this@DashboardFragment)
            adapter = recyclerViewAdapter

            val divider = DividerItemDecoration(context,VERTICAL)
            addItemDecoration(divider)
        }

        // 뷰 모델 프로바이더를 통해 뷰모델 가져오기
        // 라이프사이클을 가지고 있는 녀석을 넣어줌(자기 자신)
        // 가져올 뷰모델 클래스를 넣어서 뷰모델 가져오기
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        // 뷰 모델이 가지고있는 값의 변경사항을 관찰할 수 있는 라이브 데이터를 옵저빙한다
        viewModel.getAllBoardObservers().observe(this, Observer {
            recyclerViewAdapter.setListData(ArrayList(it))
            recyclerViewAdapter.notifyDataSetChanged()
        })

//        binding.btnSave.setOnClickListener {
//            var title = binding.name.text.toString()
//            var content = binding.email.text.toString()
//            var mood = binding.mdResult.text.toString()
//            var weather = "[test]"
//            var people = "[test]"
//            var school = "[test]"
//            var couple = "[test]"
//            var eat = "[test]"
//            var goods = "[test]"
//            var img = "test"
//
//            if(binding.btnSave.text.equals("Save")) {
//                val board = BoardEntity(0, title, content, mood, weather,people,school,couple,eat,goods,img)
//                Log.d(TAG+">>",""+board)
//                viewModel.insertBoard(board)
//            } else{
//                val board = BoardEntity(binding.name.getTag(binding.name.id).toString().toInt(), title, content, mood, weather,people,school,couple,eat,goods,img)
//                viewModel.updateBoard(board)
//                binding.btnSave.setText("Save")
//            }
//            binding.name.setText("")
//            binding.email.setText("")
//        }

//        binding.btnAdd.setOnClickListener{
//            val intent = Intent(context, DashAddActivity::class.java)
//            startActivity(intent)
//        }

    }

    override fun onDeleteBoardClickListener(board: BoardEntity) {
        viewModel.deleteBoard(board)
    }

    override fun onItemClickListener(board: BoardEntity) {
//        binding.name.setText(board.title)
//        binding.email.setText(board.content)
//
//        binding.name.setTag(binding.name.id, board.id)
//
//        binding.btnSave.setText("Update")
    }

    override fun onUpdateBoardListener(board: BoardEntity) {
        Log.d(TAG+"board", board.toString().substring(12, board.toString().length -1 ))
        var intent = Intent(context, DashAddActivity::class.java)
        intent.apply { this.putExtra("update",board.toString().substring(12, board.toString().length -1 )) }
        startActivity(intent)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}