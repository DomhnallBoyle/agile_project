using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Navigation;
using System.ComponentModel;
using System.Runtime.CompilerServices;
using System.Collections.ObjectModel;
using CSC3045_CS2.Exception;
using System.Collections.Generic;
using System;

namespace CSC3045_CS2.Pages
{
    public partial class TaskDetails : BasePage, INotifyPropertyChanged
    {
        #region Private Variables

        private ObservableCollection<TaskEstimate> _dailyEstimates = new ObservableCollection<TaskEstimate>();
        private TaskClient _taskClient;

        #endregion

        #region Public Variables

        public Task CurrentTask { get; set; }

        public ObservableCollection<TaskEstimate> DailyEstimates
        {
            get { return _dailyEstimates; }
            set
            {
                _dailyEstimates = value;
                OnPropertyChanged();
            }
        }

        #endregion

        public TaskDetails(Task currentTask)
        {
            InitializeComponent();
            CurrentPage = this.Title;
            DataContext = this;

            _taskClient = new TaskClient();

            CurrentTask = currentTask;

            try
            {
                DailyEstimates = new ObservableCollection<TaskEstimate>(_taskClient.GetTaskEstimates(
                                                                                CurrentTask.UserStory.Project.Id,
                                                                                CurrentTask.UserStory.Id,
                                                                                CurrentTask.Id));
            }
            catch (RestResponseErrorException ex)
            {
                MessageBoxUtil.ShowErrorBox(ex.Message);
            }

            Console.WriteLine("Stop Here");
        }

        #region Command Methods

        public ICommand BackCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page sprintDashboard = new SprintDashboard(CurrentTask.UserStory.Sprint, false);

                    NavigationService.GetNavigationService(this).Navigate(sprintDashboard);
                });
            }
        }

        public ICommand SaveChangesCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    try
                    {
                        List<TaskEstimate> dailyEstimates = new List<TaskEstimate>(DailyEstimates);

                        _taskClient.UpdateTaskEstimates(
                                CurrentTask.UserStory.Project.Id,
                                CurrentTask.UserStory.Id,
                                CurrentTask.Id,
                                dailyEstimates);
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBoxUtil.ShowErrorBox(ex.Message);
                    }
                });
            }
        }
        
        #endregion

        #region Binding

        private void OnPropertyChanged([CallerMemberName] string propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

        public event PropertyChangedEventHandler PropertyChanged;

        #endregion
    }
}
