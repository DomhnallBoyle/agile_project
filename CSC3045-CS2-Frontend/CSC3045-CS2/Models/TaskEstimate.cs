using RestSharp.Deserializers;
using System;
using System.ComponentModel;
using System.Runtime.CompilerServices;

namespace CSC3045_CS2.Models
{
    public class TaskEstimate : INotifyPropertyChanged
    {
        private int _estimate;

        [DeserializeAs(Name = "taskId")]
        public long TaskId { get; set; }

        [DeserializeAs(Name = "date")]
        public DateTime Date { get; set; }

        [DeserializeAs(Name = "estimate")]
        public int Estimate
        {
            get { return _estimate; }
            set
            {
                _estimate = value;
                OnPropertyChanged();
            }
        }

        public TaskEstimate() { }

        private void OnPropertyChanged([CallerMemberName] string propertyName = null)
        {
            PropertyChanged?.Invoke(this, new PropertyChangedEventArgs(propertyName));
        }

        public event PropertyChangedEventHandler PropertyChanged;
    }
}
