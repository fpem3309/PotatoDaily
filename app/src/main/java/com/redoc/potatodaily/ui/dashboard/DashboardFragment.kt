package com.redoc.potatodaily.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.redoc.potatodaily.R
import com.redoc.potatodaily.RecyclerViewAdapter
import com.redoc.potatodaily.databinding.FragmentDashboardBinding
import com.redoc.potatodaily.db.BoardEntity

class DashboardFragment : Fragment(), RecyclerViewAdapter.RowClickListener {

    //private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    lateinit var viewModel: DashboardViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


//        dashboardViewModel =
//            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textDashboard
//        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })

        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            recyclerViewAdapter = RecyclerViewAdapter(this@DashboardFragment)
            adapter = recyclerViewAdapter

            val divider = DividerItemDecoration(context,VERTICAL)
            addItemDecoration(divider)
        }

        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        viewModel.getAllBoardObservers().observe(this, Observer {
            recyclerViewAdapter.setListData(ArrayList(it))
            recyclerViewAdapter.notifyDataSetChanged()
        })

        binding.btnSave.setOnClickListener {
            var title = binding.name.text.toString()
            var content = binding.email.text.toString()

            if(binding.btnSave.text.equals("Save")) {
                val board = BoardEntity(0, title, content)
                viewModel.insertBoard(board)
            } else{
                val board = BoardEntity(binding.name.getTag(binding.name.id).toString().toInt(), title, content)
                viewModel.updateBoard(board)
                binding.btnSave.setText("Save")
            }
            binding.name.setText("")
            binding.email.setText("")
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDeleteBoardClickListener(board: BoardEntity) {
        viewModel.deleteBoard(board)
    }

    override fun onItemClickListener(board: BoardEntity) {
        binding.name.setText(board.title)
        binding.email.setText(board.content)

        binding.name.setTag(binding.name.id, board.id)

        binding.btnSave.setText("Update")
    }
}